package com.servin.trainify.exercises.data.model

import android.net.Uri

data class Exercise(
    val id: String,
    val title:String,
    val description:String,
    val mediaUrls: List<String>,
    val objective:String,
    val sportContext:String,
)


