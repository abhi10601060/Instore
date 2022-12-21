package com.example.instore.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instore.models.MainModel
import com.example.instore.networks.InstagramService
import com.example.instore.util.Resource
import retrofit2.Response

class ContentRepository(private val api : InstagramService ) {
    private val postLivedata  = MutableLiveData<Resource<MainModel>>()
    private val reelLiveData  = MutableLiveData<Resource<MainModel>>()
    private val igtvLiveData  = MutableLiveData<Resource<MainModel>>()

    val post : LiveData<Resource<MainModel>>
        get() = postLivedata

    val reel : LiveData<Resource<MainModel>>
        get() = reelLiveData

    val igtv : LiveData<Resource<MainModel>>
        get() = igtvLiveData

    suspend fun getContent(url :String , type : Int){
        when(type){
            1 ->{
                postLivedata.postValue(Resource.Loading<MainModel>())
                val response = api.getContent(url)
                postLivedata.postValue(handleContent(response))
            }

            2-> {
                reelLiveData.postValue(Resource.Loading<MainModel>())
                val response = api.getContent(url)
                reelLiveData.postValue(handleContent(response))
            }

            3 -> {
                igtvLiveData.postValue(Resource.Loading<MainModel>())
                val response = api.getContent(url)
                igtvLiveData.postValue(handleContent(response))
            }
        }
    }

    private fun handleContent(response: Response<MainModel>) : Resource<MainModel>{
        if (response.body() != null){
            return Resource.Success<MainModel>(response.body()!!)
        }
        return Resource.Error<MainModel>(message = response.message())
    }

}