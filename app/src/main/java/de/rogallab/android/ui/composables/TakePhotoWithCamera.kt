package de.rogallab.android.ui.composables

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.data.io.writeImageToInternalStorage
import de.rogallab.android.ui.theme.paddings

// https://fvilarino.medium.com/using-activity-result-contracts-in-jetpack-compose-14b179fb87de

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TakePhotoWithCamera(
   onImagePathChanged: (String?) -> Unit,  // Event ↑
   tag: String = "ok>TakePhotoWithCamera."
) {

   LogComp(tag,"Start")

   val bitmapState = remember { mutableStateOf<Bitmap?>(value = null) }
   val context = LocalContext.current

   // callback camera
   val launcher = rememberLauncherForActivityResult(
      ActivityResultContracts.TakePicturePreview()
   ) { // it:Bitmap? ->
      LogFun(tag,"Photo as bitmap ${it?.byteCount}")
      bitmapState.value = it

      // save bitmap to internal storage of the app
      bitmapState.value?.let { bitmap ->
         writeImageToInternalStorage(context, bitmap)?.let { uriPath:String? ->
            LogFun(tag,"Path $uriPath")
            onImagePathChanged(uriPath) // Event ↑
         }
      }
   }

   // start camera
   Chip(
      modifier = Modifier.padding(vertical = MaterialTheme.paddings.small),
      leadingIcon = { Icons.Outlined.AddAPhoto },
      onClick = {
         LogFun("ok>Take a photo       .", "Click")
         launcher.launch()
      }
   ) {
      Text(
         modifier = Modifier.padding(all = MaterialTheme.paddings.small),
         text ="Bitte nehmen Sie ein Foto auf",
         style = MaterialTheme.typography.body1
      )
   }

   CheckCameraPermission()


}



