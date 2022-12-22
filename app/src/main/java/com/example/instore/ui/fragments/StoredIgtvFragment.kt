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

class StoredIgtvFragment : Fragment(R.layout.fragment_stored_igtv) {

    lateinit var storedIgtvRV : RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storedIgtvRV = view.findViewById(R.id.stored_igtv_RV)

        getIgtvs()
    }

    private fun getIgtvs() {

        val igtvDirectory = File("${Environment.getExternalStorageDirectory()}/Download/Instore/igtv" )

        val igtvs = igtvDirectory.listFiles()

        Log.d("ABHI", "getReels: ${igtvs.toString()} -- ${igtvDirectory.toString()}")

        val igtvVideos = igtvs?.filter {
            it.absolutePath.endsWith(".mp4")
        }

        if (igtvVideos != null) {
            for (igtv in igtvVideos){
                Log.d("ABHI", "gwtReels: ${igtv.toString()}")
            }
        }

        val adapter = igtvVideos?.let { context?.let { it1 -> StoredPostAdapter(it, it1) } }
        storedIgtvRV.adapter = adapter
        storedIgtvRV.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)

    }
}