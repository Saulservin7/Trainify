package com.servin.trainify.presentation.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.servin.trainify.R
import com.servin.trainify.ui.theme.BluePrimary

@Composable
fun StarRating(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    filledColor: Color = BluePrimary,
    borderColor: Color = Color.White,
    starSize: Dp? = 32.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,


        ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(
                    id = if (i <= rating) R.drawable.star_filled else R.drawable.star
                ),
                contentDescription = "Star $i",
                tint = if (i <= rating) filledColor else borderColor,
                modifier = Modifier
                    .size(starSize ?: 32.dp)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}


@Composable
@Preview

fun StarRatingPreview() {
    StarRating(
        rating = 3,
        onRatingChanged = {},
        modifier = Modifier
    )
}