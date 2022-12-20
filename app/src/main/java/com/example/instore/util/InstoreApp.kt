package com.example.instore.util

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.instore.networks.InstagramService
import com.example.instore.networks.RetroInstance
import com.example.instore.repos.ContentRepository


class InstoreApp : Application() {

    lateinit var contentRepository: ContentRepository

    override fun onCreate() {
        super.onCreate()
        createRepo()
    }



    private fun createRepo() {
        val api = RetroInstance.getInstance().create(InstagramService::class.java)
        contentRepository = ContentRepository(api)
    }
}