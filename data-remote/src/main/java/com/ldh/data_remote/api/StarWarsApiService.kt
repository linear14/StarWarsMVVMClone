package com.ldh.data_remote.api

import com.ldh.data_remote.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApiService {

    @GET("people/")
    suspend fun searchCharacters(@Query("search") params: String): SearchResponse

}