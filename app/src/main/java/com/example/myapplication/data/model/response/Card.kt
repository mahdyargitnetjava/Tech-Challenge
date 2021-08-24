package com.example.myapplication.data.model.response

import kotlinx.serialization.Serializable

@Serializable
class Card{
    val cards: Array<CardInfo> = arrayOf()
    @Serializable
    data class CardInfo(
        val code: Int,
        val title: String,
        val description: String,
        val image: String? = null,
        val sound: String? = null,
        val tag: String
    )
}
