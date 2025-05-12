package com.servin.trainify.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.servin.trainify.ui.theme.BluePrimary

@Composable
fun DaysSelect() {
    SelectableDaysChipsTwoRows()
}

@Composable
fun SelectableDaysChipsTwoRows() {
    val firstRowDays = listOf("Lunes", "Martes", "Miércoles", "Jueves")
    val secondRowDays = listOf("Viernes", "Sábado", "Domingo")
    val selectedDays = remember { mutableStateListOf<String>() }

    Column() {
        Text(
            "Programación",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            firstRowDays.forEach { day ->
                DayChip(day, selectedDays)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            secondRowDays.forEach { day ->
                DayChip(day, selectedDays)
            }
        }


    }
}

@Composable
fun DayChip(day: String, selectedDays: SnapshotStateList<String>) {
    val isSelected = day in selectedDays

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) BluePrimary else Color.Transparent)
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(50)
            )
            .clickable {
                if (isSelected) selectedDays.remove(day) else selectedDays.add(day)
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = day,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}


@Composable
@Preview
fun DaysSelectPreview() {
    DaysSelect()
}