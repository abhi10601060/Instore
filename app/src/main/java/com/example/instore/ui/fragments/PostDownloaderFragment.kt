package com.example.instore.ui.fragments

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.instore.R
import com.example.instore.models.MainModel
import com.example.instore.ui.activities.MainActivity
import com.example.instore.util.Resource
import com.example.instore.viewmodels.ContentViewModel

class PostDownloaderFragment : Fragment(R.layout.fragment_post_downloader) {

    lateinit var postEditText : EditText
    lateinit var viewContentBtn : Button
    lateinit var downloadBtn : Button
    lateinit var postImage: ImageView
    lateinit var contentViewModel: ContentViewModel
    lateinit var postProgress: ProgressBar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)


        contentViewModel = (activity as MainActivity).contentViewModel

        viewContentBtn.setOnClickListener(View.OnClickListener {
            val url = postEditText.text.toString().trim()
            if (url.contains("https://www.instagram.com/p/")){
                contentViewModel.getContent(url , 1)
            }
        })

        downloadBtn.setOnClickListener(View.OnClickListener {
            if (context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.WRITE_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED){
                (activity as MainActivity).askPermission()
            }
            else{
                (activity as MainActivity).handlePostDownload()
            }
        })

        contentViewModel.post.observe(viewLifecycleOwner , androidx.lifecycle.Observer {
            when(it){

                is Resource.Success<MainModel> ->  {
                    postProgress.visibility = View.GONE
                    Glide.with(view)
                        .asBitmap()
                        .load(Uri.parse(it.data?.graphql?.shortcode_media?.display_url))
                        .into(postImage)
                }
                is Resource.Error<MainModel> -> {
                    postProgress.visibility = View.GONE
                    Toast.makeText(context, "invalid url", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading<MainModel> -> {
                    postProgress.visibility = View.VISIBLE
                }
            }
        })
    }


    private fun initViews(view: View) {
        postEditText = view.findViewById(R.id.edt_post)
        viewContentBtn = view.findViewById(R.id.btn_View_Post)
        postImage =view.findViewById(R.id.img_post)
        postProgress = view.findViewById(R.id.postProgressBar)
        downloadBtn = view.findViewById(R.id.btn_download_post)
    }
}