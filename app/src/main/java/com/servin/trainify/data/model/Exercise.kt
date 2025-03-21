package com.servin.trainify.data.model

data class Exercise(
    val id: String,
    val title:String,
    val description:String,
    val imageURL:String?,
    val videoURL:String?,
    val objective:String,
    val sportContext:String,
)


