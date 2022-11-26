package de.rogallab.android.ui.composables

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import de.rogallab.android.domain.utilities.LogFun
import kotlinx.coroutines.launch

@Composable
fun ShowErrorMessage(
   scaffoldState: ScaffoldState, // State ↓
   errorMessage: String?,        // State ↓
   actionLabel: String?,         // State ↓
   onErrorDismiss: () -> Unit,   // Event ↑
   onErrorAction: () -> Unit,    // Event ↑
   tag: String = "ok>ShowErrorMessage   ."
) {

   val coroutineScope = rememberCoroutineScope()

   errorMessage?.let { message ->
      coroutineScope.launch {
         LogFun(tag,"launch Snackbar")
         val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Long
         )
         when (snackbarResult) {
            SnackbarResult.Dismissed       -> {
               LogFun(tag,"SnackbarResult.Dismissed")
               onErrorDismiss()
            }
            SnackbarResult.ActionPerformed -> {
               LogFun(tag,"SnackbarResult.ActionPerformed")
               onErrorAction()
            }
         }
      }
   }
}