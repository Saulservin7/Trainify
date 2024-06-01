package com.servin.trainify.navigation

sealed class Routes (val title:String,  val icon:Int,val route:String){

    object Home: Routes("Home",0,"home")
    object Exercises: Routes("Exercises",0,"exercises")

    object Config: Routes("Config",0,"config")

}