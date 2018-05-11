package net.tutorial.powernap.fragments


import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import kotlinx.android.synthetic.main.fragment_play.*
import net.tutorial.powernap.R
import java.util.*


class PlayFragment : Fragment() {

    private val TAG = "PlayFragment";
    private var player = MediaPlayer()
    private var seekBarUpdateHandler = Handler()
    private var position = 0

    lateinit var updateSeekBar: Runnable

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) : View? {

        val view = inflater.inflate(R.layout.fragment_play, container, false)
        val args = arguments
        position = args?.getInt("position")!!

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createLocalViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }

    private fun createLocalViews() {
        createMediaPlayer()

        updateSeekBar = Runnable {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = player.currentPosition.toLong()
            player_current_time.text = DateFormat.format("mm:ss", cal).toString()

            seekBar.progress = player.currentPosition
            seekBarUpdateHandler.postDelayed(updateSeekBar, 50)
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player.seekTo(progress)
                    val cal = Calendar.getInstance(Locale.ENGLISH)
                    cal.timeInMillis = player.currentPosition.toLong()
                    player_current_time.text = DateFormat.format("mm:ss", cal).toString()
                }
            }

        })

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

        music_back.setOnClickListener({
            if (position > 0 ) {
                position--
                createMediaPlayer()
            }
        })

        music_next.setOnClickListener({
            if (position < 7) {
                position++
                createMediaPlayer()
            }
        })
    }

    private fun createMediaPlayer() {
        val resId = when (position) {
            0 -> R.raw.alpha
            1 -> R.raw.beta
            2 -> R.raw.creativity_increase
            3 -> R.raw.mental_refresher_alpha
            4 -> R.raw.mental_refresher_beta
            5 -> R.raw.relaxation_2
            6 -> R.raw.sleep_induction
            else -> R.raw.alpha
        }
        player = MediaPlayer.create(activity, resId)
        player.start()
        player.pause()

        seekBar.max = player.duration

        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = player.duration.toLong()
        player_duration.text = DateFormat.format("mm:ss", cal).toString()
        player_current_time.text = "00:00"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ");
        seekBarUpdateHandler.removeCallbacks((updateSeekBar))
    }
}
