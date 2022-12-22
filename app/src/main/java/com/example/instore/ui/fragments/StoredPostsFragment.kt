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

class StoredPostsFragment : Fragment(R.layout.fragment_stored_posts) {

    lateinit var postsRv : RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsRv = view.findViewById(R.id.postsRV)

        getPosts()
    }

    fun getPosts(){

        val postsDirectory = File("${Environment.getExternalStorageDirectory()}/Download/Instore/posts" )

        val posts = postsDirectory.listFiles()

        Log.d("ABHI", "getPosts: ${posts.toString()} -- ${postsDirectory.toString()}")

       val imagePosts = posts?.filter {
           it.absolutePath.endsWith(".jpg") ||
           it.absolutePath.endsWith(".png") ||
           it.absolutePath.endsWith(".jpeg")
       }

        if (imagePosts != null) {
            for (post in imagePosts){
                Log.d("ABHI", "getPosts: ${post.toString()}")
            }
        }

        val adapter = imagePosts?.let { context?.let { it1 -> StoredPostAdapter(it, it1) } }
        postsRv.adapter = adapter
        postsRv.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
    }
}