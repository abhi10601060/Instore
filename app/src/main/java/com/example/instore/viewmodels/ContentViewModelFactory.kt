package com.example.instore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instore.repos.ContentRepository

class ContentViewModelFactory(private val repo : ContentRepository ) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContentViewModel(repo) as T
    }
}