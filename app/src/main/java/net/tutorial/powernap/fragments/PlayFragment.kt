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
import net.tutorial.powernap.R
import net.tutorial.powernap.interfaces.FragmentListener
import java.util.*


class PlayFragment : Fragment() {

    private val TAG = "PlayFragment";
    var player = MediaPlayer()
    var seekBarUpdateHandler  = Handler()

    lateinit var updateSeekBar: Runnable;
    lateinit var callbackFragment: FragmentListener
    lateinit var seekBar: SeekBar
    lateinit var playerDuration: TextView
    lateinit var playerCurrentPosition: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        val args = arguments
        val position = args?.getInt("position")

        when (position) {
            0 -> player = MediaPlayer.create(activity, R.raw.alpha)
            1 -> player = MediaPlayer.create(activity, R.raw.beta)
        }
        player.start()
        player.pause()

        initLocalViews(view)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is FragmentListener){
            callbackFragment = context
            Log.i(TAG, "onAttach: FragmentListener has triggered");
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ");
        player.stop()
    }

    private fun initLocalViews(view: View) {
        seekBar = view.findViewById(R.id.seekBar)
        playerDuration = view.findViewById(R.id.player_duration)
        playerCurrentPosition = view.findViewById(R.id.player_current_time)

        val arrawDown = view.findViewById<Button>(R.id.arrow_down)
        val playPauseView = view.findViewById<PlayPauseView>(R.id.play_pause_view)


        arrawDown.setOnClickListener({callbackFragment.fragmentCallback("PlayFragment Button Clicked")})
        playPauseView.setOnClickListener({
            if (playPauseView.isPlay) {
                seekBarUpdateHandler.postDelayed(updateSeekBar, 0);
                player.seekTo(player.currentPosition)
                player.start()
            } else {
                seekBarUpdateHandler.removeCallbacks(updateSeekBar)
                player.pause()
            }
            playPauseView.toggle()
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
                }
            }

        })
        updateSeekBar = Runnable {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.setTimeInMillis(player.currentPosition.toLong())
            playerCurrentPosition.text = DateFormat.format("mm:ss", cal).toString()

            seekBar.setProgress(player.currentPosition)
            seekBarUpdateHandler.postDelayed(updateSeekBar, 50)
        }

        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.setTimeInMillis(player.duration.toLong())
        playerDuration.text = DateFormat.format("mm:ss", cal).toString()
    }
}
