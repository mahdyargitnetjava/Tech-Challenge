package com.example.myapplication.net

import com.example.myapplication.data.model.response.Card
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface NetworkService {

  @GET("tempelate.json")
  suspend fun fetchCardsList(): ApiResponse<List<Card>>

}
