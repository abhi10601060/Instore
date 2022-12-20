package com.example.instore.networks

import com.example.instore.models.MainModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface InstagramService {

    @GET
    suspend fun getContent(@Url url : String) : Response<MainModel>
}