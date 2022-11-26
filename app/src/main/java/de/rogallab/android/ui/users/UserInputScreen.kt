package de.rogallab.android.ui.users

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import de.rogallab.android.R
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.ui.composables.ShowErrorMessage
import de.rogallab.android.ui.navigation.NavScreen
import de.rogallab.android.ui.theme.AppTheme
import de.rogallab.android.ui.theme.paddings

@Composable
fun UserInputScreen(
   navController: NavController,
   viewModel: UsersViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {

   val tag: String = "ok>ContactInputScreen ."

   // testing the snackbar
   viewModel.onError("Test SnackBar: Fehlermeldung ...")

   val scaffoldState = rememberScaffoldState()

   ShowErrorMessage(
      scaffoldState = scaffoldState,                  // State ↓
      errorMessage = viewModel.errorMessage,          // State ↓
      actionLabel = "Abbrechen",                      // State ↓
      onErrorDismiss = { viewModel.onErrorDismiss() }, // Event ↑
      onErrorAction = { viewModel.onErrorAction() },  // Event ↑
   )

   AppTheme {
      Scaffold(
         scaffoldState = scaffoldState,
         topBar = {
            TopAppBar(
               title = { Text(stringResource(R.string.contact_input)) },
               navigationIcon = {
                  IconButton(onClick = {
                     navController.navigate(route = NavScreen.UsersList.route) {
                        popUpTo(route = NavScreen.UsersList.route) { inclusive = true }
                     }
                  }) {
                     Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back))
                  }
               }
            )
         },
         snackbarHost = { snackBarHostState ->
            SnackbarHost(hostState = snackBarHostState) { data ->
               Snackbar(
                  snackbarData = data,
                  actionOnNewLine = true
               )
            }
         }
      ) {  paddingValues: PaddingValues ->

         Column(
            modifier = Modifier
               .wrapContentWidth()
               .padding(bottom = paddingValues.calculateBottomPadding())
               .padding(horizontal = MaterialTheme.paddings.small)
               .statusBarsPadding()
               .verticalScroll(
                  state = rememberScrollState(),
                  enabled = true,
                  reverseScrolling = true
               )
         ) {

            InputNameMailPhone(
               firstName = viewModel.firstName,                          // State ↓
               onFirstNameChange = { viewModel.onFirstNameChange(it) }, // Event ↑
               lastName = viewModel.lastName,                            // State ↓
               onLastNameChange = { viewModel.onLastNameChange(it) },   // Event ↑
               email = viewModel.email,                                  // State ↓
               onEmailChange = { viewModel.onEmailChange(it) },         // Event ↑
               phone = viewModel.phone,                                  // State ↓
               onPhoneChange = { viewModel.onPhoneChange(it) }          // Event ↑
            )

            SelectAndShowImage(
               imagePath = viewModel.imagePath,                          // State ↓
               onImagePathChanged = { viewModel.onImagePathChange(it) } // Event ↑
            )

            Button(
               modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = MaterialTheme.paddings.default),
               onClick = {
                  LogFun(tag, "onClickHandler()")
                  // val id = viewModel.write()
                  navController.navigate(route = NavScreen.UsersList.route) {
                     popUpTo(route = NavScreen.UsersList.route) { inclusive = true }
                  }
               }
            ) {
               Text(
                  modifier = Modifier
                     .padding(
                        top = MaterialTheme.paddings.tiny,
                        bottom = MaterialTheme.paddings.tiny
                     ),
                  style = MaterialTheme.typography.button,
                  text = stringResource(R.string.save)
               )
            }
         }
      }
   }
}