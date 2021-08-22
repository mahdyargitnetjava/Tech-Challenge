package com.example.myapplication.data.model.response

import kotlinx.serialization.Serializable

@Serializable
class CardResponse{
    val cards: Array<CardInfo> = arrayOf()
    @Serializable
    data class CardInfo(
        val code: Int,
        val title: String,
        val description: String,
        val image: String?,
        val sound: String?,
        val tag: String
    )
}
