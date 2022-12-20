package com.example.instore.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.instore.R
import com.example.instore.models.MainModel
import com.example.instore.ui.activities.MainActivity
import com.example.instore.util.Resource
import com.example.instore.viewmodels.ContentViewModel

class ReelDownloaderFragment : Fragment(R.layout.fragment_reel_downloader) {

    lateinit var reelEditText : EditText
    lateinit var viewReelBtn : Button
    lateinit var downloadReel : Button
    lateinit var reelVideoView : VideoView
    lateinit var reelProgress : ProgressBar

    lateinit var viewModel : ContentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).contentViewModel

        initViews(view)

        viewReelBtn.setOnClickListener(View.OnClickListener {
            val url = reelEditText.text.toString().trim()
            if (url.contains("https://www.instagram.com/reel/")){
                viewModel.getContent(url)
            }

        })
        viewModel.content.observe(viewLifecycleOwner , androidx.lifecycle.Observer {
            when(it){

                is Resource.Success<MainModel> ->  {
                    reelProgress.visibility = View.GONE
                    it.data?.graphql?.shortcode_media?.video_url?.let { showVideo(it) }
                }
                is Resource.Error<MainModel> -> {
                    reelProgress.visibility = View.GONE
                    Toast.makeText(context, "invalid url", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading<MainModel> -> {
                    reelProgress.visibility = View.VISIBLE
                }
            }
        })


    }

    private fun showVideo(url: String) {
        reelVideoView.setVideoURI(Uri.parse(url))

        val mediaController = MediaController(context)
        reelVideoView.setMediaController(mediaController)
        mediaController.setAnchorView(reelVideoView)
        reelVideoView.start()

    }

    private fun initViews(view : View) {
        reelEditText = view.findViewById(R.id.edt_reel)
        viewReelBtn = view.findViewById(R.id.btn_View_Reel)
        downloadReel = view.findViewById(R.id.btn_download_reel)
        reelVideoView = view.findViewById(R.id.vdo_reel)
        reelProgress = view.findViewById(R.id.reelProgressBar)
    }
}