package com.example.instore.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.instore.R
import com.example.instore.adapter.ContentViewPagerAdapter
import com.example.instore.util.InstoreApp
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
}