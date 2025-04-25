package com.servin.trainify.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.servin.trainify.ui.theme.BluePrimary

@Composable
fun Ranking(modifier: Modifier, size: Dp) {
    Column(
        modifier = modifier,

        ) {

        ConstraintLayout() {
            val (punctuation, rating) = createRefs()

            Text("(4.5)", fontSize = 20.sp, modifier = Modifier.constrainAs(punctuation){
                start.linkTo(rating.start)
                end.linkTo(rating.end)

            })
            StarRating(
                rating = 4,
                onRatingChanged = {},
                modifier = modifier.constrainAs(rating){
                    top.linkTo(punctuation.bottom)
                },
                filledColor = BluePrimary,
                borderColor = Color.White,
                starSize = size,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            )
        }

    }
}


@Composable
@Preview
fun RankingPreview() {
    Ranking(modifier = Modifier, size = 15.dp)
}