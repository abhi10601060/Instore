package com.example.instore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instore.models.MainModel
import com.example.instore.repos.ContentRepository
import com.example.instore.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContentViewModel(val repo : ContentRepository) : ViewModel() {

    val post : LiveData<Resource<MainModel>>
        get() = repo.post

    val reel : LiveData<Resource<MainModel>>
        get() = repo.reel

    val igtv : LiveData<Resource<MainModel>>
        get() = repo.igtv


    fun getContent(rawUrl : String , type : Int){
        val url = getJsonUrl(rawUrl)
        viewModelScope.launch(Dispatchers.IO){
            repo.getContent(url , type)
        }
    }

    fun getJsonUrl(rawUrl: String) : String{
        val suffix = "__a=1&__d=dis"
        val newUrl = rawUrl.replaceAfter("/?" , suffix , "abcde" )
        return newUrl
    }


}