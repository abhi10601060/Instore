package com.example.instore.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.example.instore.R
import com.example.instore.adapter.ContentViewPagerAdapter
import com.example.instore.adapter.StoredContentViewPagerAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout

class StorageActivity : AppCompatActivity() {

    lateinit var toolbar: MaterialToolbar
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        initViews()

        setSupportActionBar(toolbar)
        setupViewPagerWithTablayout()

    }

    private fun setupViewPagerWithTablayout() {

        val contentViewPager = StoredContentViewPagerAdapter(supportFragmentManager , lifecycle)
        viewPager.adapter = contentViewPager

        tabLayout.setOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener{
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
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar_storage_activity)
        tabLayout = findViewById(R.id.stored_activity_tablayout)
        viewPager = findViewById(R.id.storage_viewPager)
    }
}