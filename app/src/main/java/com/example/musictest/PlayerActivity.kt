package com.example.musictest

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.musictest.databinding.ActivityPlayerBinding


class PlayerActivity : AppCompatActivity() {
    companion object{
        lateinit var songList: ArrayList<Song>
        private var songPosition: Int = 0
       var player:MediaPlayer?=null
    }
    private lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
       songPosition= intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")){
            "MusicAdapter"->{
    songList= ArrayList()
                songList.addAll(MainActivity.song)
                if(player==null)
                    player= MediaPlayer()
                player!!.reset()
                player!!.setDataSource(songList[songPosition].path)
                player!!.prepare()
                player!!.start()

            }
        }
    }
}   