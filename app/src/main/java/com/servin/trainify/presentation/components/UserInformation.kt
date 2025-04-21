package com.servin.trainify.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UserInformation(
    modifier: Modifier,
    userData: List<Pair<String, String>>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val scaleFactor = screenWidth / 430.dp

    val cardWidth = 350.dp * scaleFactor
    val cardHeight = 300.dp * scaleFactor

    Card(
        modifier = modifier
            .padding(16.dp)
            .width(cardWidth)
            .height(cardHeight),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp * scaleFactor),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            userData.forEachIndexed { index, pair ->
                DataRow(pair.first, pair.second)
                if (index < userData.lastIndex) {
                    HorizontalDivider(thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun DataRow(fieldName: String, fieldValue: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(
            text = fieldName,
            modifier = Modifier.weight(1f),
            color = Color.White,
        )
        Text(
            text = fieldValue,
            modifier = Modifier.weight(1f),
            color = Color.White,
        )
    }
}



