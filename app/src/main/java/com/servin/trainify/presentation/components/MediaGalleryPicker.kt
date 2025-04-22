package com.servin.trainify.presentation.components

import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import android.net.Uri
import coil3.compose.AsyncImage
import androidx.core.net.toUri
import androidx.compose.foundation.lazy.items



/**
 * Permite seleccionar y mostrar múltiples imágenes y/o videos.
 * @param mediaUrls Lista mutable de URLs de medios (imágenes/videos).
 * @param onAddMedia Callback que agrega un nuevo medio a la lista externa (en ViewModel, etc).
 */
@Composable
fun MediaGalleryPicker(
    mediaUrls: List<String>,
    onAddMedia: (String) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            // En lugar de sobrescribir, agregamos el nuevo medio a la lista existente
            uri?.let { newUri ->
                onAddMedia(newUri.toString())
            }
        }
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(mediaUrls) { url ->
                if (url.endsWith(".mp4") || url.endsWith(".mov")) {
                    AndroidView(
                        factory = {
                            VideoView(it).apply {
                                setVideoURI(url.toUri())
                                setOnPreparedListener { mediaPlayer -> mediaPlayer.isLooping = true; start() }
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .width(200.dp)
                            .height(120.dp)
                    )
                } else {
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(120.dp)
                    )
                }
            }
        }

        Button(
            onClick = { launcher.launch("image/* video/*") },
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Agregar foto o video")
        }
    }
}
