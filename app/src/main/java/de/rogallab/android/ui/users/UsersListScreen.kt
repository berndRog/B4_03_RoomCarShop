package de.rogallab.android.ui.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import de.rogallab.android.domain.entities.User
import de.rogallab.android.domain.useCases.UiState
import de.rogallab.android.ui.composables.ShowErrorMessage
import de.rogallab.android.ui.navigation.NavScreen
import de.rogallab.android.ui.theme.AppTheme
import de.rogallab.android.ui.theme.elevations
import de.rogallab.android.ui.theme.paddings

@Composable
fun UsersListScreen(
   navController: NavController,
   viewModel: UsersViewModel = hiltViewModel(),
) {
   val tag = "ok>UsersListScreen ."

   val scaffoldState: ScaffoldState = rememberScaffoldState()

   AppTheme {
      Scaffold(
         scaffoldState = scaffoldState,
         isFloatingActionButtonDocked = false,
         floatingActionButtonPosition = FabPosition.End,
         floatingActionButton = {
            FloatingActionButton(
               backgroundColor = MaterialTheme.colors.secondaryVariant,
               onClick = { navController.navigate(NavScreen.UserInput.route) }
            ) {
               Icon(Icons.Default.Add, "")
            }
         },
         snackbarHost = { snackBarHostState ->
            SnackbarHost(hostState = snackBarHostState) { data ->
               Snackbar(
                  //modifier =  Modifier.border(2.dp, MaterialTheme.colors.secondary),
                  snackbarData = data,
                  actionOnNewLine = true
               )
            }
         },
      ) { paddingValues ->
         val left = paddingValues.calculateLeftPadding(LayoutDirection.Ltr)

         //   observer
         val uiState: UiState = viewModel.stateOfUiState.value

         when (uiState) {
            UiState.Loading -> {
               Column(modifier = Modifier.fillMaxSize(),
                  verticalArrangement = Arrangement.Center,
                  horizontalAlignment = Alignment.CenterHorizontally
               ) {
                  // AnimatedVisibility(visible = true) {  ... }
                  CircularProgressIndicator(modifier = Modifier.size(150.dp))
               }
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
               var items = mutableListOf<User>()
               if( uiState.data is List<*>) items = uiState.data as MutableList<User>
               LazyColumn(state = rememberLazyListState()) {
                  items(items = items) { user ->
                     ContactCard(
                        user = user,
                        onClick = {
                           navController.navigate(
                              route = NavScreen.UserDetail.route + "/${user.id.toString()}"
                           )
                        }
                     )
                  }
               }
            }
         }
      }
   }
}

@Composable
fun ContactCard(
   user: User,
   elevation: Dp = MaterialTheme.elevations.card,
   onClick: () -> Unit
) {
   Card(
      shape = MaterialTheme.shapes.small,
      modifier = Modifier
         .padding(all = MaterialTheme.paddings.tiny)
         .fillMaxWidth()
         .clickable(onClick = onClick),
      elevation = elevation
   ) {
      Row(
         modifier = Modifier.padding(
            all = MaterialTheme.paddings.small
         )

      ) {
         user.imagePath?.let { path ->
            AsyncImage(
               model = path,
               contentDescription = "Bild des Kontakts",
               modifier = Modifier
                  .size(width = 75.dp, height = 75.dp)
                  .clip(RoundedCornerShape(percent = 5)),
               alignment = Alignment.Center,
               contentScale = ContentScale.Crop
            )
         }

         Column(
            modifier = Modifier
               .padding(start = MaterialTheme.paddings.small)
         ) {
            Text(
               text = "${user.firstName} ${user.lastName}",
               modifier = Modifier.wrapContentWidth(align = Alignment.Start),
               style = MaterialTheme.typography.body1
            )
            user.email?.let{
               Text(
                  text = it,
                  modifier = Modifier.wrapContentWidth(align = Alignment.Start),
                  style = MaterialTheme.typography.body2
               )

            }
            user.phone?.let {
               Text(
                  text = it,
                  modifier = Modifier
                     .wrapContentWidth(align = Alignment.Start),
                  style = MaterialTheme.typography.body2
               )
            }
         }
      }
   }
}
