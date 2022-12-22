package com.example.instore.ui.fragments

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instore.R
import com.example.instore.adapter.StoredPostAdapter
import java.io.File

class StoredReelsFragment : Fragment(R.layout.fragment_stored_reels) {

    lateinit var storedReelsRV : RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storedReelsRV = view.findViewById(R.id.stored_reels_RV)

        getReels()
    }

    private fun getReels() {

        val reelsDirectory = File("${Environment.getExternalStorageDirectory()}/Download/Instore/reels" )

        val reels = reelsDirectory.listFiles()

        Log.d("ABHI", "getReels: ${reels.toString()} -- ${reelsDirectory.toString()}")

        val reelVideos = reels?.filter {
            it.absolutePath.endsWith(".mp4")
        }

        if (reelVideos != null) {
            for (reel in reelVideos){
                Log.d("ABHI", "gwtReels: ${reel.toString()}")
            }
        }

        val adapter = reelVideos?.let { context?.let { it1 -> StoredPostAdapter(it, it1) } }
        storedReelsRV.adapter = adapter
        storedReelsRV.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)

    }
}