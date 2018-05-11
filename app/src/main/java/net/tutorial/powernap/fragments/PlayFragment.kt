package net.tutorial.powernap.fragments


import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.ohoussein.playpause.PlayPauseView
import kotlinx.android.synthetic.main.fragment_play.*
import net.tutorial.powernap.R
import net.tutorial.powernap.interfaces.FragmentListener
import java.util.*


class PlayFragment : Fragment() {

    private var player = MediaPlayer()
    private var seekBarUpdateHandler = Handler()

    lateinit var updateSeekBar: Runnable

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) : View? {

        val view = inflater.inflate(R.layout.fragment_play, container, false)
        val args = arguments
        val position = args?.getInt("position")

        when (position) {
            0 -> player = MediaPlayer.create(activity, R.raw.alpha)
            1 -> player = MediaPlayer.create(activity, R.raw.beta)
        }
        player.start()
        player.pause()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }

    private fun initLocalViews() {
        play_pause_view.setOnClickListener({
            if (play_pause_view.isPlay) {
                seekBarUpdateHandler.postDelayed(updateSeekBar, 0);
                player.seekTo(player.currentPosition)
                player.start()
            } else {
                seekBarUpdateHandler.removeCallbacks(updateSeekBar)
                player.pause()
            }
            play_pause_view.toggle()
        })

        seekBar.max = player.duration
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player.seekTo(progress)
                    val cal = Calendar.getInstance(Locale.ENGLISH)
                    cal.setTimeInMillis(player.currentPosition.toLong())
                    player_current_time.text = DateFormat.format("mm:ss", cal).toString()
                }
            }

        })
        updateSeekBar = Runnable {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.setTimeInMillis(player.currentPosition.toLong())
            player_current_time.text = DateFormat.format("mm:ss", cal).toString()

            seekBar.setProgress(player.currentPosition)
            seekBarUpdateHandler.postDelayed(updateSeekBar, 50)
        }

        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.setTimeInMillis(player.duration.toLong())
        player_duration.text = DateFormat.format("mm:ss", cal).toString()
    }
}
