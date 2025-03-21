package com.servin.trainify.domain.model

import androidx.annotation.DrawableRes
import com.servin.trainify.R


sealed class ExerciseCategory(val title: String, @DrawableRes val image: Int) {
    object Brazo : ExerciseCategory(title = "Brazos", image = R.drawable.brazos)
    object Pierna : ExerciseCategory(title = "Piernas", image = R.drawable.pierna)
    object Espalda : ExerciseCategory(title = "Espalda", image = R.drawable.espalda)
    object Pecho : ExerciseCategory(title = "Pecho", image = R.drawable.pecho)
    object Abdomen : ExerciseCategory(title = "Abdomen", image = R.drawable.abdomen)
    object Cardio : ExerciseCategory(title = "Cardio", image = R.drawable.cardio)
    object FullBody : ExerciseCategory(title = "Full Body", image = R.drawable.fullbody)
    object Estiramiento : ExerciseCategory(title = "Estiramiento", image = R.drawable.estiramiento)
    object Explosividad : ExerciseCategory(title = "Explosividad", image = R.drawable.explosividad)
    object Agilidad : ExerciseCategory(title = "Agilidad", image = R.drawable.agilidad)
    object Velocidad : ExerciseCategory(title = "Velocidad", image = R.drawable.velocidad)
    object Fuerza : ExerciseCategory(title = "Fuerza", image = R.drawable.fuerza)
    object Resistencia : ExerciseCategory(title = "Resistencia", image = R.drawable.resistencia)

    companion object {
        val allCategories: List<ExerciseCategory> by lazy {
            listOf(
                Brazo,
                Pierna,
                Espalda,
                Pecho,
                Abdomen,
                Cardio,
                FullBody,
                Estiramiento,
                Explosividad,
                Agilidad,
                Velocidad,
                Fuerza,
                Resistencia
            )
        }
    }
}

