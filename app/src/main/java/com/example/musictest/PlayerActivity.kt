package com.example.musictest

import android.media.MediaPlayer
import android.media.session.MediaSession
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.musictest.databinding.ActivityPlayerBinding
import wseemann.media.FFmpegMediaMetadataRetriever
import java.util.concurrent.TimeUnit

private const val TAG = "PlayerActivity"

class PlayerActivity : AppCompatActivity() {
    companion object {

        lateinit var songList: ArrayList<Song>
        lateinit var songLists: List<Song>
        private var songPosition: Int = 0
        var player: MediaPlayer? = null
        var isPlaying: Boolean = false
        private lateinit var mediaSession:MediaSession
        private lateinit var runnable: Runnable

    }

    private lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        sendData()

        binding.playPause.setOnClickListener {
            if (isPlaying) pauseMusic()
            else playMusic()
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

               // binding.seekBar.max = player!!.duration / 1000
                /* binding.tvSeekBarStart.text = progress.toString()
                 if (seekBar != null) {
                     binding.tvSeekBarEnd.text = seekBar.max.toString()
                 }*/

                 if (player != null && fromUser)
                     player!!.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
        runnable= Runnable {
            this@PlayerActivity.binding.tvSeekBarStart.text =formatDuration(player!!.currentPosition.toLong())
            this@PlayerActivity. binding.seekBar.progress=player!!.currentPosition
            Handler(Looper.getMainLooper()).postDelayed(runnable,200)

        }
        Handler(Looper.getMainLooper()).postDelayed(runnable,0)


    }

    fun formatDuration(duration: Long): String {
        val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) -
                minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun sendData() {
        songPosition = intent.getIntExtra("index", 0)
        when (intent.getStringExtra("class")) {
            "MusicAdapter" -> {
                songList = ArrayList()
                songList.addAll(MainActivity.song)
                setLayout()
                createMediaPlayer()

            }
        }
       // binding.seekBar.max = player!!.duration / 1000
        binding.seekBar.max = player!!.duration

    }

    private fun createMediaPlayer() {
        try {
            if (player == null)
                player = MediaPlayer()
            player!!.reset()
            player!!.setDataSource(songList[songPosition].path)
            player!!.prepare()
            player!!.start()
            isPlaying = true
            binding.playPause.setImageResource(R.drawable.ic_pause)
            binding.tvSeekBarStart.text =formatDuration(player!!.currentPosition.toLong())
            binding.tvSeekBarEnd.text = formatDuration(player!!.duration.toLong())
            binding.seekBar.progress=0
            //binding.seekBar.max = player!!.duration / 1000
        } catch (e: Exception) {
            return
        }

    }

    private fun playMusic() {
        binding.playPause.setImageResource(R.drawable.ic_pause)
        isPlaying = true
        player!!.start()
    }

    private fun pauseMusic() {
        binding.playPause.setImageResource(R.drawable.ix_play)
        isPlaying = false
        player!!.pause()
    }

    private fun setLayout() {
// val image: String? = (songList.get(songPosition).path)
        val image = songList.get(songPosition).path?.let { getAlbumArt(it) }
        if (image != null) {
            Glide.with(this).load(image)
                .fitCenter().circleCrop().diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.profileImage)
        } else {
            Glide.with(this).load(R.drawable.ic_music_note50).into(binding.profileImage)
        }
        binding.songName.setText(songList.get(songPosition).title)
        binding.artistName.setText(songList.get(songPosition).artist)
    }

    fun getAlbumArt(uri: String): ByteArray? {

        val retriever: FFmpegMediaMetadataRetriever = FFmpegMediaMetadataRetriever()
        retriever.setDataSource(uri)
        val art: ByteArray? = retriever.embeddedPicture
        retriever.release()
        return art

    }



}



