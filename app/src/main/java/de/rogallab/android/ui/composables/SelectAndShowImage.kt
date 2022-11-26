package de.rogallab.android.ui.users

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.ui.composables.SelectPhotoFromGallery
import de.rogallab.android.ui.composables.TakePhotoWithCamera
import de.rogallab.android.ui.theme.paddings

@Composable
fun SelectAndShowImage(
   imagePath: String?,                                 // State ↓
   onImagePathChanged: (String?) -> Unit,              // Event ↑
   tag: String = "ok>ContactSelectAndShow"
) {

   LogComp(tag,"Start")

   Row(
      modifier = Modifier
         .padding(vertical = MaterialTheme.paddings.small)
         .fillMaxWidth()
   ) {
      imagePath?.let { path:String ->                  // State ↓
         LogComp(tag, "ContactImage $path")
         AsyncImage(
            model = path,
            contentDescription = "Bild des Kontakts",
            modifier = Modifier
               .size(width = 150.dp, height = 200.dp)
               .clip(RoundedCornerShape(percent = 5)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
         )
      }
//    ?: run {          // else ... show chips
      .run {            // and ... always show chips
         Column(
            modifier = Modifier
               .padding(start = MaterialTheme.paddings.small)
               .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
         ) {
            SelectPhotoFromGallery(
               onImagePathChanged = onImagePathChanged // Event ↑
            )

            TakePhotoWithCamera(
               onImagePathChanged = onImagePathChanged // Event ↑
            )
         }
      }
   }
}