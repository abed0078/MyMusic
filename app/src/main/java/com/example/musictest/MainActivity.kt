package com.example.musictest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musictest.databinding.ActivityMainBinding
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private var REQUEST_VIDEO_CODE = 456
    private var songList: ArrayList<Song>? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        makeRequest()
        getMp3Files(this)


        binding.rv.adapter = MusicAdapter(this, getMp3Files(this))
        binding.rv.layoutManager = LinearLayoutManager(this)
    }


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
    fun getMp3Files(context: Context): MutableList<Song> {
        val fileList = mutableListOf<Song>()
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
                fileList.add(Song(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))))
                //, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
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
}


