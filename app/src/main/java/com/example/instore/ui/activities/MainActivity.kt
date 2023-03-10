package com.example.instore.ui.activities

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.instore.R
import com.example.instore.adapter.ContentViewPagerAdapter
import com.example.instore.models.MainModel
import com.example.instore.util.InstoreApp
import com.example.instore.util.Resource
import com.example.instore.viewmodels.ContentViewModel
import com.example.instore.viewmodels.ContentViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    lateinit var toolbar : MaterialToolbar
    lateinit var contentTabs : TabLayout
    lateinit var viewPager : ViewPager2
    lateinit var contentViewModel : ContentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        contentTabs = findViewById(R.id.content_tabLayout)
        viewPager = findViewById(R.id.contents_viewPager)

        val contentRepository = (application as InstoreApp).contentRepository
        contentViewModel = ViewModelProvider(this , ContentViewModelFactory(contentRepository)).get(ContentViewModel::class.java)

        setSupportActionBar(toolbar)
        setupViewPagerWithTablayout()

        askPermission()

        if (intent!=null){
            if (intent.action.equals(Intent.ACTION_SEND) && intent.type.equals("text/plain")){
                Log.d("ABHI", "onCreate: $intent.getStringExtra(Intent.EXTRA_TEXT) ")
                handleIntent(intent.getStringExtra(Intent.EXTRA_TEXT))
                Log.d("ABHI", "onCreate: ${contentViewModel.receivedPath} ")
            }
        }
    }

    private fun setupViewPagerWithTablayout() {
        val contentViewPager = ContentViewPagerAdapter(supportFragmentManager , lifecycle)
        viewPager.adapter = contentViewPager

        contentTabs.setOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { viewPager.setCurrentItem(it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                contentTabs.selectTab(contentTabs.getTabAt(position))
            }
        })

    }

    fun askPermission() {
        if (ContextCompat.checkSelfPermission(this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE) , 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){

            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission granted...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun handlePostDownload() {
        if (contentViewModel.post.value is Resource.Success<MainModel>){
            (contentViewModel.post.value as Resource.Success<MainModel>).data?.graphql?.shortcode_media?.display_url?.let {
                download(it, "Downloading Post...", "/Instore/posts/"+ java.util.Calendar.getInstance().timeInMillis.toString() + ".jpg")
            }
        }
        else{
            Toast.makeText(this, "Invalid Url ....", Toast.LENGTH_SHORT).show()
        }
    }

    fun handleReelDownload() {
        if (contentViewModel.reel.value is Resource.Success<MainModel>){
            (contentViewModel.reel.value as Resource.Success<MainModel>).data?.graphql?.shortcode_media?.video_url?.let {
                download(it, "Downloading Reel...", "/Instore/reels/"+ java.util.Calendar.getInstance().timeInMillis.toString() + ".mp4")
            }
        }
        else{
            Toast.makeText(this, "Invalid Url ....", Toast.LENGTH_SHORT).show()
        }
    }

    fun handleIgtvDownload() {
        if (contentViewModel.igtv.value is Resource.Success<MainModel>){
            (contentViewModel.igtv.value as Resource.Success<MainModel>).data?.graphql?.shortcode_media?.video_url?.let {
                download(it, "Downloading igtv...", "/Instore/igtv/"+ java.util.Calendar.getInstance().timeInMillis.toString() + ".mp4")
            }
        }
        else{
            Toast.makeText(this, "Invalid Url ....", Toast.LENGTH_SHORT).show()
        }
    }

    private fun download(url : String , title : String, path : String) {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI and DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(title)
        request.setDescription("------")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        request.allowScanningByMediaScanner()
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,path )

        val manager = (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager)
        manager.enqueue(request)
        Toast.makeText(this, "Downloading started...", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.storage_menu_item -> {
                val intent = Intent(this , StorageActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.storage_menu , menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun handleIntent(path: String?) {
        if (path != null && path.contains("https://www.instagram.com/")){
            contentViewModel.receivedPath = intent.getStringExtra(Intent.EXTRA_TEXT)
            if (path.contains("/p/")){
                viewPager.setCurrentItem(0)
            }
            else if (path.contains("/reel/")){
                viewPager.setCurrentItem(1)
            }
            else if(path.contains("/tv/")){
                viewPager.setCurrentItem(2)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentViewModel.receivedPath = null
    }
}