package com.example.instore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instore.models.MainModel
import com.example.instore.repos.StorageRepository
import com.example.instore.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class StorageViewModel(val repo : StorageRepository) : ViewModel() {

    val post : LiveData<MutableList<File>>
        get() = repo.post

    val reel : LiveData<MutableList<File>>
        get() = repo.reel

    val igtv : LiveData<MutableList<File>>
        get() = repo.igtv

    fun loadStoredContent(){
            repo.loadPosts()
            repo.loadReels()
            repo.loadigtv()
    }

}