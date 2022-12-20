package com.example.instore.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instore.models.MainModel
import com.example.instore.networks.InstagramService
import com.example.instore.util.Resource
import retrofit2.Response

class ContentRepository(private val api : InstagramService ) {
    private val contentLivedata  = MutableLiveData<Resource<MainModel>>()

    val content : LiveData<Resource<MainModel>>
    get() = contentLivedata

    suspend fun getContent(url :String){
        contentLivedata.postValue(Resource.Loading<MainModel>())
        val response = api.getContent(url)
        contentLivedata.postValue(handleContent(response))
    }

    private fun handleContent(response: Response<MainModel>) : Resource<MainModel>{
        if (response.body() != null){
            return Resource.Success<MainModel>(response.body()!!)
        }
        return Resource.Error<MainModel>(message = response.message())
    }

}