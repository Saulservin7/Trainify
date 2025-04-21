package com.servin.trainify.auth.domain.model

data class User(
    val id: String,
    val name:String="",
    val email:String="",
    val age:String?=null,
    val height:String?=null,
    val weight:String?=null,
    val photoUrl:String?=null,
    val gender:String?=null,
    )
