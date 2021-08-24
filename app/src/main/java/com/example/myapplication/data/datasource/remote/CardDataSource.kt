package com.example.myapplication.data.datasource.remote

import com.example.myapplication.data.model.response.Card
import com.example.myapplication.net.NetworkService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class CardDataSource @Inject constructor(
  private val networkService: NetworkService
) {

  suspend fun fetchCardsList(): ApiResponse<List<Card>> = networkService.fetchCardsList()

}
