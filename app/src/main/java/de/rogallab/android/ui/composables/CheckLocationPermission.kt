package de.rogallab.android.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.ui.theme.paddings

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckLocationPermission() {

   val multiplePermissionsState = rememberMultiplePermissionsState(
      listOf(
         android.Manifest.permission.ACCESS_COARSE_LOCATION,
         android.Manifest.permission.ACCESS_FINE_LOCATION,
      )
   )

   if (multiplePermissionsState.allPermissionsGranted) {
      LogFun("ok>CheckLocationPermis.:", "location permissions are granted")
   }
   else {
      val allPermissionsRevoked =
         multiplePermissionsState.permissions.size ==
            multiplePermissionsState.revokedPermissions.size

      val textToShow = if (!allPermissionsRevoked) {
         // approximate location permission granted, but not the FINE
         "The app needs the FINE location permission too!"
      }
      else if (multiplePermissionsState.shouldShowRationale) {
         // Both location permissions have been denied, show the rationale
         "Location permissions are required. " +
            "Please grant the permissions."
      }
      else {
         // First time
         "This app requires fine and approximate location permissions."
      }

      val buttonText = if (!allPermissionsRevoked) {
         "Allow FINE location permission"
      }
      else {
         "Request permissions"
      }

      Column(
         modifier = Modifier
            .padding(top = MaterialTheme.paddings.huge)
            .padding(horizontal = MaterialTheme.paddings.small)
            .fillMaxSize(),
         ) {
         Text(
            text = textToShow,
            style = MaterialTheme.typography.body1
         )
         Button(modifier = Modifier
            .padding(
               top = MaterialTheme.paddings.medium,
               bottom = MaterialTheme.paddings.tiny
            )
            .fillMaxWidth(),
            onClick = {
            multiplePermissionsState.launchMultiplePermissionRequest()
         }) {
            Text(
               text = buttonText,
               style = MaterialTheme.typography.button
            )
         }
      }
   }
}