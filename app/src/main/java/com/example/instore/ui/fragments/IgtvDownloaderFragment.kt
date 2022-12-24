package com.example.instore.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.instore.R
import com.example.instore.models.MainModel
import com.example.instore.ui.activities.MainActivity
import com.example.instore.util.Resource
import com.example.instore.viewmodels.ContentViewModel

class IgtvDownloaderFragment : Fragment(R.layout.fragment_igtv_downloader) {

    lateinit var igtvEditText : EditText
    lateinit var viewigtvBtn : Button
    lateinit var downloadIgtv : Button
    lateinit var igtvVideoView : VideoView
    lateinit var igtvProgress : ProgressBar

    lateinit var viewModel : ContentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)


        viewModel = (activity as MainActivity).contentViewModel

        viewigtvBtn.setOnClickListener(View.OnClickListener {
            deleteReceivedIgtv()
            val url = igtvEditText.text.toString().trim()
            if (url.contains("https://www.instagram.com/tv/")){
                viewModel.getContent(url , 3)
            }

        })
        viewModel.igtv.observe(viewLifecycleOwner , androidx.lifecycle.Observer {
            when(it){

                is Resource.Success<MainModel> ->  {
                    igtvProgress.visibility = View.GONE
                    it.data?.graphql?.shortcode_media?.video_url?.let { showVideo(it) }
                }
                is Resource.Error<MainModel> -> {
                    igtvProgress.visibility = View.GONE
                    Toast.makeText(context, "invalid url", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading<MainModel> -> {
                    igtvProgress.visibility = View.VISIBLE
                }
            }
        })

        downloadIgtv.setOnClickListener(View.OnClickListener {
            if (context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.WRITE_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED){
                (activity as MainActivity).askPermission()
            }
            else{
                (activity as MainActivity).handleIgtvDownload()
            }
        })

        handleReceivedIgtv()

    }

    private fun showVideo(url: String) {

        igtvVideoView.setVideoURI(Uri.parse(url))

        val mediaController = MediaController(context)
        igtvVideoView.setMediaController(mediaController)
        mediaController.setAnchorView(igtvVideoView)

        igtvVideoView.start()
    }

    private fun initViews(view : View) {
        igtvEditText = view.findViewById(R.id.edt_igtv)
        viewigtvBtn = view.findViewById(R.id.btn_View_igtv)
        downloadIgtv = view.findViewById(R.id.btn_download_igtv)
        igtvVideoView = view.findViewById(R.id.vdo_igtv)
        igtvProgress = view.findViewById(R.id.igtvProgressBar)
    }

    private fun deleteReceivedIgtv() {
        if (viewModel.receivedPath != null && viewModel.receivedPath!!.contains("/tv/")){
            viewModel.receivedPath = null
        }
    }

    private fun handleReceivedIgtv() {
        if (viewModel.receivedPath != null && viewModel.receivedPath!!.contains("/tv/")){
            igtvEditText.setText(viewModel.receivedPath)
            viewigtvBtn.callOnClick()
        }
    }

}