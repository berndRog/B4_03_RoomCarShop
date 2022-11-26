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
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.R
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.domain.useCases.UiState
import de.rogallab.android.ui.composables.ShowErrorMessage
import de.rogallab.android.ui.theme.AppTheme
import de.rogallab.android.ui.navigation.NavScreen
import de.rogallab.android.ui.theme.paddings
import java.util.*

@Composable
fun UserDetailScreen(
   navController: NavController,
   viewModel: UsersViewModel = hiltViewModel<UsersViewModel>(),
   contactId: String?
) {
   val tag = "ok>UserDetailScreen    ."
   val scaffoldState = rememberScaffoldState()

   contactId?.let {
      LogComp(tag, "readById $contactId")
      viewModel.readById(UUID.fromString(contactId))
   }

   AppTheme {
      Scaffold(
         scaffoldState = scaffoldState,
         topBar = {
            TopAppBar(
               title = { Text(stringResource(R.string.contact_detail)) },
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
         }

      ) { paddingValues ->
         val bottom = paddingValues.calculateBottomPadding()

         Column(
            modifier = Modifier
               .wrapContentWidth()
               .padding(all = MaterialTheme.paddings.small)
               .verticalScroll(
                  state = rememberScrollState(),
                  enabled = true,
                  reverseScrolling = true
               )
         ) {

            //   observer
            val uiState: UiState = viewModel.stateOfUiState.value
            when(uiState) {
               UiState.Loading -> {

               }
               UiState.Retrying -> {}
               is UiState.Error -> {
                  ShowErrorMessage(
                     scaffoldState = scaffoldState,                   // State ↓
                     errorMessage = uiState.message,                  // State ↓
                     actionLabel = "Abbrechen",                       // State ↓
                     onErrorDismiss = { viewModel.onErrorDismiss() }, // Event ↑
                     onErrorAction = { viewModel.onErrorAction() },   // Event ↑
                  )
               }
               is UiState.Success<*> -> {
                  ContactDetailSuccess(
                     contactId = contactId,
                     navController = navController,
                     firstName = viewModel.firstName,                         // State ↓
                     onFirstNameChange = { viewModel.onFirstNameChange(it) }, // Event ↑
                     lastName = viewModel.lastName,                           // State ↓
                     onLastNameChange = { viewModel.onLastNameChange(it) },   // Event ↑
                     email = viewModel.email,                                 // State ↓
                     onEmailChange = { viewModel.onEmailChange(it) },         // Event ↑
                     phone = viewModel.phone,                                 // State ↓
                     onPhoneChange = { viewModel.onPhoneChange(it) },         // Event ↑
                     imagePath = viewModel.imagePath,                         // State ↓
                     onImagePathChange = { viewModel.onImagePathChange(it) }, // Event ↑
                     onUpdate = { viewModel.update(it) }                      // Event ↑
                  )
               }
            }
         }
      }
   }
}


@Composable
private fun ContactDetailSuccess(
   contactId: String?,
   navController: NavController,
   firstName: String,                     // State ↓
   onFirstNameChange: (String) -> Unit,   // Event ↑
   lastName: String,                      // State ↓
   onLastNameChange: (String) -> Unit,    // Event ↑
   email: String?,                        // State ↓
   onEmailChange: (String) -> Unit,       // Event ↑
   phone: String?,                        // State ↓
   onPhoneChange: (String) -> Unit,       // Event ↑
   imagePath: String?,                    // State ↓
   onImagePathChange: (String?) -> Unit,  // Event ↑
   onUpdate: (UUID) -> Unit,
   tag: String = "ContactDetailSuccess"
) {
   InputNameMailPhone(
      firstName = firstName,                          // State ↓
      onFirstNameChange = { onFirstNameChange(it) },  // Event ↑
      lastName = lastName,                            // State ↓
      onLastNameChange = { onLastNameChange(it) },    // Event ↑
      email = email,                                  // State ↓
      onEmailChange = { onEmailChange(it) },          // Event ↑
      phone = phone,                                  // State ↓
      onPhoneChange = { onPhoneChange(it) }           // Event ↑
   )

   SelectAndShowImage(
      imagePath = imagePath,                          // State ↓
      onImagePathChanged = { onImagePathChange(it) } // Event ↑
   )

   Button(
      modifier = Modifier
         .fillMaxWidth()
         .padding(top = MaterialTheme.paddings.default),
      onClick = {
         onUpdate(UUID.fromString(contactId))
         navController.navigate(route = NavScreen.UsersList.route) {
            popUpTo(route = NavScreen.UsersList.route) { inclusive = true }
         }
         LogFun(tag, "onClickHandler()")
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