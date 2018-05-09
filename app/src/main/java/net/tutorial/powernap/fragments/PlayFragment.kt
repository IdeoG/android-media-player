package net.tutorial.powernap.fragments


import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.tutorial.powernap.R
import android.widget.Button
import com.ohoussein.playpause.PlayPauseView
import net.tutorial.powernap.services.MusicPlayerService
import net.tutorial.powernap.interfaces.FragmentListener


class PlayFragment : Fragment() {

    private val TAG = "PlayFragment";
    private lateinit var callbackFragment: FragmentListener
    private lateinit var service: Intent
    private lateinit var player: MediaPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.i(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        view.findViewById<Button>(R.id.arrow_down)
                .setOnClickListener({callbackFragment.fragmentCallback("PlayFragment Button Clicked")})

        val args = arguments
        val position = args?.getInt("position")

        when (position) {
            0 -> player = MediaPlayer.create(activity, R.raw.alpha)
            1 -> player = MediaPlayer.create(activity, R.raw.beta)
        }
        player.start()
        player.pause()
        Log.i(TAG, "onCreateView: player.duration = ${player.duration} ");

        Log.i(TAG, "onCreateView: position = $position");

        val playPauseView = view.findViewById<PlayPauseView>(R.id.play_pause_view)
        playPauseView.setOnClickListener({
            if (playPauseView.isPlay) {

                player.seekTo(player.currentPosition)
                player.start()
                Log.i(TAG, "onCreateView: player.isPlaying = ${player.isPlaying}, " +
                        "player.currentPosition = ${player.currentPosition}");
                player.start()
            } else {
                player.pause()
            }
            playPauseView.toggle()
        })

        service = Intent(activity, MusicPlayerService::class.java)
        activity?.startService(service)

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
    }
}
