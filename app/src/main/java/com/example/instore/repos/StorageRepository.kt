package com.example.instore.repos

import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.File

class StorageRepository {

    private val postLivedata  = MutableLiveData<MutableList<File>>()
    private val reelLiveData  = MutableLiveData<MutableList<File>>()
    private val igtvLiveData  = MutableLiveData<MutableList<File>>()

    val post : LiveData<MutableList<File>>
        get() = postLivedata

    val reel : LiveData<MutableList<File>>
        get() = reelLiveData

    val igtv : LiveData<MutableList<File>>
        get() = igtvLiveData


    fun loadPosts(){
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
        if (imagePosts != null){
            postLivedata.postValue(imagePosts.toMutableList())
        }
    }

     fun loadReels(){
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
        if (reelVideos != null) {
            reelLiveData.postValue(reelVideos.toMutableList())
        }

    }

     fun loadigtv(){
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
        if (igtvVideos!=null){
            igtvLiveData.postValue(igtvVideos.toMutableList())
        }
    }


}