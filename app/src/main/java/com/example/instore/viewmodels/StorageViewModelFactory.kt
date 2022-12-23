package com.example.instore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instore.repos.StorageRepository

class StorageViewModelFactory(private val repo : StorageRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StorageViewModel(repo) as T
    }
}