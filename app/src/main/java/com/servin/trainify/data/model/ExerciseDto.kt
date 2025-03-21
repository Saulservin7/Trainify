package com.servin.trainify.data.model

data class ExerciseDto(
    val title:String="",
    val description:String="",
    val imageURL:String?="",
    val videoURL:String?="",
    val objective:String="",
    val sportContext:String="",
){
    fun toExercise(id:String):Exercise{
        return Exercise(
            id = id,
            title = title,
            description = description,
            imageURL = imageURL,
            videoURL = videoURL,
            objective = objective,
            sportContext = sportContext
        )
    }
}
