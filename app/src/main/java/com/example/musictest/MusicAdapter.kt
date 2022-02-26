package com.example.musictest

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musictest.databinding.RecyclerviewItemBinding


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

        }

        init {
            Binding.root.setOnClickListener {
                listener.onItemClicked(adapterPosition)
            }
        }

    }
}

