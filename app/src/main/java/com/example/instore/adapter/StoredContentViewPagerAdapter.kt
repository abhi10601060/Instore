package com.example.instore.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instore.ui.fragments.StoredIgtvFragment
import com.example.instore.ui.fragments.StoredPostsFragment
import com.example.instore.ui.fragments.StoredReelsFragment

class StoredContentViewPagerAdapter(val fragmentManager: FragmentManager , val lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager , lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){

            1 -> return StoredReelsFragment()

            2-> return StoredIgtvFragment()
        }

        return StoredPostsFragment()
    }
}