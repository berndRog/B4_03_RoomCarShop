package de.rogallab.android.ui.composables

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Images.Media.getBitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Portrait
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.data.io.writeImageToInternalStorage
import de.rogallab.android.ui.theme.paddings

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectPhotoFromGallery(
   onImagePathChanged: (String?) -> Unit,  // Event ↑
   tag: String = "ok>SelectPhotoFromGale."
) {

   LogComp(tag,"Start")

   var bitmap:Bitmap?
   val context = LocalContext.current

   // callback for result from photo gallery
   val launcher = rememberLauncherForActivityResult(
      contract = ActivityResultContracts.GetContent()
   ) { uri: Uri? ->
      // get bitmap from content resolver (photo gallery)
      uri?.let {
         LogFun(tag,"Get Bitmap ${it.path}")
         if (Build.VERSION.SDK_INT < 28) {
            bitmap = getBitmap(context.contentResolver, it)
         } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            bitmap = ImageDecoder.decodeBitmap(source)
         }
         // save bitmap to internal storage of the app
         bitmap?.let { bitmap ->
            writeImageToInternalStorage(context, bitmap)?.let { uriPath:String? ->
               LogFun("ok>SelectPhotoFromGale.", "Storage ${uriPath}")
               onImagePathChanged(uriPath)  // Event ↑
            }
         }
      }
   }


   Column {

      Chip(
         modifier = Modifier.padding(vertical = MaterialTheme.paddings.small),
         leadingIcon = { Icons.Outlined.Portrait },
         onClick = {
            LogFun("ok>SelectPhotoFromGale.", "Click")
            launcher.launch("image/*")
         }
      ) {
         Text(
            modifier = Modifier.padding(all = MaterialTheme.paddings.small),
            text = "Bitte wählen Sie ein Foto aus",
            style = MaterialTheme.typography.body1
         )
      }
   }
}