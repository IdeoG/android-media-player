package net.tutorial.powernap.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class MusicPlayerService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private lateinit var player: MediaPlayer

    override fun onCompletion(mp: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPrepared(mp: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val TAG = "MusicPlayerService";
    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "onBind: ");
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ");
        initMusicPlayer()
    }

    private fun initMusicPlayer() {
        player = MediaPlayer()
        player.setOnPreparedListener(this)
        player.setOnCompletionListener(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ");
    }

}
