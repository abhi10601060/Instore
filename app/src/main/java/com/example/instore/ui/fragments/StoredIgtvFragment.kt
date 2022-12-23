package com.example.instore.ui.fragments

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instore.R
import com.example.instore.adapter.StoredPostAdapter
import com.example.instore.ui.activities.StorageActivity
import com.example.instore.viewmodels.StorageViewModel
import java.io.File


class StoredIgtvFragment : Fragment(R.layout.fragment_stored_igtv) {

    lateinit var storedIgtvRV : RecyclerView
    lateinit var viewModel : StorageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storedIgtvRV = view.findViewById(R.id.stored_igtv_RV)
        viewModel = (activity as StorageActivity).viewModel


        viewModel.igtv.observe(viewLifecycleOwner , Observer{ igtvPosts ->
            val adapter = igtvPosts?.let { activity?.let { it1 -> StoredPostAdapter(it, it1) } }
            storedIgtvRV.adapter = adapter
            storedIgtvRV.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)

        })
    }

}