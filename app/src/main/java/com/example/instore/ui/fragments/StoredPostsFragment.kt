package com.example.instore.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instore.R
import com.example.instore.adapter.StoredPostAdapter
import com.example.instore.ui.activities.StorageActivity
import com.example.instore.viewmodels.StorageViewModel

class StoredPostsFragment : Fragment(R.layout.fragment_stored_posts) {

    lateinit var postsRv : RecyclerView
    lateinit var viewModel: StorageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsRv = view.findViewById(R.id.postsRV)

        viewModel = (activity as StorageActivity).viewModel


        viewModel.post.observe(viewLifecycleOwner , Observer{ imagePosts ->
            val adapter = imagePosts?.let { activity?.let { it1 -> StoredPostAdapter(it, it1) } }
            postsRv.adapter = adapter
            postsRv.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)

        })
    }

}