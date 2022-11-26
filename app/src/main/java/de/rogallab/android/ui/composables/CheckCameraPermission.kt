package de.rogallab.android.ui.composables

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.*
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.ui.theme.paddings

/*
<uses-feature android:name="android.hardware.camera"
                android:required="false" />
  <uses-permission android:name="android.permission.CAMERA" />
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
  <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

 */


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckCameraPermission(
   tag: String = "ok>CheckCameraPermiss ."
) {

   LogComp(tag, "Start")

   // Camera permission state
   val cameraPermissionState = rememberPermissionState(
      Manifest.permission.CAMERA
   )

   when (cameraPermissionState.status) {
      // If the camera permission is granted, do noting
      is PermissionStatus.Granted -> {
         LogComp(tag, "camera permission granted")

      }
      is PermissionStatus.Denied -> {
         val textToShow = if ((cameraPermissionState.status as PermissionStatus.Denied)
               .shouldShowRationale) {
            // permission has been denied, show the rationale (=Begründung)
            "Die Kamera ist wichtig für diese App. Bitte gewähren Sie den Zugriff."
         }
         else {
            // First time
            "Die App erfordert den Zugriff auf die Kamera, um ein Foto auszunehmen."
         }
         LogComp(tag, "$textToShow")

         Column(
            modifier = Modifier
               .padding(top = MaterialTheme.paddings.small)
               .fillMaxSize(),
         ) {
            Text(
               text = textToShow,
               style = MaterialTheme.typography.body1
            )
            Button(
               modifier = Modifier
                  .padding(
                     top = MaterialTheme.paddings.tiny,
                     bottom = MaterialTheme.paddings.huge
                  )
                  .fillMaxWidth(),
               onClick = {
                  // Note that this dialog might not appear on the screen
                  // if the user doesn't want to be asked again or has denied
                  // the permission multiple times.
                  // This behavior varies depending on the Android level API.
                  cameraPermissionState.launchPermissionRequest()
               }
            ) {
               Text(     // request permission
                  text = "Erlaubnis anfordern",
                  style = MaterialTheme.typography.button
               )
            }
         }
      }
   }
}