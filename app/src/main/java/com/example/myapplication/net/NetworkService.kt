package com.example.myapplication.net

import com.example.myapplication.data.model.response.CardResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface NetworkService {

  @GET("tempelate.json")
  suspend fun fetchCardsList(): ApiResponse<List<CardResponse>>

}
