package com.example.musictest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musictest.databinding.ActivityMainBinding
import kotlin.system.exitProcess


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    /*  private lateinit var song: ArrayList<Song>
      private var songPosition: Int = 0
      private lateinit var player: MediaPlayer
     private var musicBound = false*/
    companion object {
        private var songPosition: Int = 0
        private var musicService: MusicService? = null
        private var playIntent: Intent? = null
        private var REQUEST_VIDEO_CODE = 456

        //use this as a list
        lateinit var song: ArrayList<Song>
        private var songList: ArrayList<Song>? = null
        private lateinit var binding: ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        makeRequest()
        //  getMp3Files(this)
        initializeLayout()

        /* binding.rv.adapter = MusicAdapter(this, getMp3Files(this))
         binding.rv.layoutManager = LinearLayoutManager(this)*/

        ////////////////
        /*  binding.rv.adapter = adapter
          binding.rv.layoutManager = LinearLayoutManager(this)*/
        binding.buttonEnd.setOnClickListener {
            stopService(playIntent)
            musicService = null
            exitProcess(0)
        }


    }

    //connect to the service
    /* val musicConnection: ServiceConnection = object : ServiceConnection {
         override fun onServiceConnected(name: ComponentName, service: IBinder) {
             val binder = service as MusicBinder
             //get service
             musicService = binder.service
             //pass list
             songList?.let { musicService!!.setList(it) }
             musicBound = true
         }

         override fun onServiceDisconnected(name: ComponentName) {
             musicBound = false
         }
     }*/

    /* override fun onStart() {
         super.onStart()
         if(playIntent==null){
             playIntent= Intent(this,MusicService::class.java)
             bindService(playIntent,musicConnection,Context.BIND_AUTO_CREATE)
             startService(playIntent)
         }
     }*/

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this!!,
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.WAKE_LOCK
            ),
            REQUEST_VIDEO_CODE
        )
    }

    @SuppressLint("Range")
    //fun getMp3Files(context: Context): MutableList<Song> {
   // val fileList = mutableListOf<Song>()
    fun getMp3Files(context: Context): ArrayList<Song> {
        val fileList = ArrayList<Song>()
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA
        )


        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                fileList.add(
                    Song(
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    )
                )
                //, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                // , cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                //  , cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))));
            }
        }
        if (cursor != null) {
            cursor.close()
        }
        return fileList
    }

    private fun initializeLayout() {
        song = getMp3Files(this)
        var adapter = MusicAdapter(this, song)
        adapter.setOnItemClickListener(object : MusicAdapter.onItemClickListener {
            override fun onItemClicked(position: Int) {
                val intent = Intent(this@MainActivity, PlayerActivity::class.java)
                intent.putExtra("index", position)
                intent.putExtra("class", "MusicAdapter")
                ContextCompat.startActivity(this@MainActivity, intent, null)


            }


        })
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)
    }
    /* fun songPicked(view: View) {
         musicService?.setSong(view.getTag().toString().toInt())
         musicService?.playSong()
     }

     override fun onDestroy() {
         stopService(playIntent)
         musicService = null
         super.onDestroy()
     }*/


}


