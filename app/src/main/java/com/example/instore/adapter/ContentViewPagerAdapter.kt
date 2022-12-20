package com.example.instore.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instore.ui.fragments.IgtvDownloaderFragment
import com.example.instore.ui.fragments.PostDownloaderFragment
import com.example.instore.ui.fragments.ReelDownloaderFragment

class ContentViewPagerAdapter(val fragmentManager: FragmentManager , val lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager , lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
       when(position){
           1 -> return ReelDownloaderFragment()

           2 -> return IgtvDownloaderFragment()
       }
        return PostDownloaderFragment()
    }
}