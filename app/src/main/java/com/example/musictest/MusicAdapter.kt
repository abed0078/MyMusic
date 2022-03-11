package com.example.musictest

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.musictest.databinding.RecyclerviewItemBinding
import wseemann.media.FFmpegMediaMetadataRetriever


class MusicAdapter(private val context: Context, private val songsList: List<Song>) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    private val TAG = "MusicAdapter"
    private lateinit var player: MediaPlayer
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClicked(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder at position $position")
        val songList = songsList[position]
        holder.bind(songList)

        // holder.bind(songsList.get(position))


    }

    override fun getItemCount() = songsList.size


    inner class ViewHolder(val Binding: RecyclerviewItemBinding, listener: onItemClickListener) :
        RecyclerView.ViewHolder(Binding.root) {
        fun bind(songsList: Song) {
            // Binding.songArtist.text = songsList.artist
            Binding.songTitle.text = songsList.title
            Binding.artist.text = songsList.artist
            val image:ByteArray?= songsList.path?.let { getAlbumArt(it) }
                if(image!=null){   Glide.with(context).load(image)
                    .fitCenter().circleCrop().diskCacheStrategy(DiskCacheStrategy.NONE).into(Binding.circleImageView)}
            else{
                Glide.with(context).load(R.drawable.ic_music_note).into(Binding.circleImageView)
            }

        }

        init {
            Binding.root.setOnClickListener {
                listener.onItemClicked(adapterPosition)
            }
        }

    }

     fun getAlbumArt(uri: String): ByteArray? {

       val retriever: FFmpegMediaMetadataRetriever = FFmpegMediaMetadataRetriever()
        retriever.setDataSource(uri)
        val art: ByteArray? =retriever.embeddedPicture
        retriever.release()
        return art

    }
    }




