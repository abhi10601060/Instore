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

    val content : LiveData<Resource<MainModel>>
    get() = repo.content

    fun getContent(rawUrl : String){
        val url = getJsonUrl(rawUrl)
        viewModelScope.launch(Dispatchers.IO){
            repo.getContent(url)
        }
    }

    fun getJsonUrl(rawUrl: String) : String{
        val suffix = "__a=1&__d=dis"
        val newUrl = rawUrl.replaceAfter("/?" , suffix , "abcde" )
        return newUrl
    }


}