package com.example.musictest

import android.app.Service
import android.content.ContentUris
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.provider.MediaStore
import android.util.Log


open class MusicService : Service()
    //MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
  //  MediaPlayer.OnCompletionListener
    {
        /* private val musicBind: IBinder = MusicBinder()
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var song: ArrayList<Song>
    private var songPosition: Int = 0

    override fun onCreate() {
        super.onCreate()
        songPosition = 0
        mediaPlayer = MediaPlayer()
        initMusicPlayer()
    }

    open fun initMusicPlayer() {
        //set player properties
        mediaPlayer.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnErrorListener(this)
    }

    open fun setList(theSongs: ArrayList<Song>) {
        song = theSongs
    }

    class MusicBinder : Binder() {
        val service: MusicService
            get() = this.service
    }

    override fun onBind(intent: Intent?): IBinder? {
        return musicBind
    }

    override fun onUnbind(intent: Intent?): Boolean {
        mediaPlayer.stop()
        mediaPlayer.release()
        return false
    }

    override fun onPrepared(mp: MediaPlayer?) {
        if (mp != null) {
            mp.start()
        }
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCompletion(mp: MediaPlayer?) {
        TODO("Not yet implemented")
    }

   open fun playSong() {
//play a song
       mediaPlayer.reset()
       //get song
       val playSong: Song = song.get(songPosition)
       val currSong: Long = playSong.id
       //set uri
       val trackUri: Uri = ContentUris.withAppendedId(
           MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
           currSong
       )
       try {
           mediaPlayer.setDataSource(applicationContext, trackUri)
       } catch (e: Exception) {
           Log.e("MUSIC SERVICE", "Error setting data source", e)
       }
       mediaPlayer.prepareAsync()
    }
    open fun setSong(songIndex: Int) {
        songPosition = songIndex
    }*/
        override fun onBind(intent: Intent?): IBinder? {
            TODO("Not yet implemented")
        }

    }