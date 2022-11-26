package de.rogallab.android.ui.users

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.rogallab.android.domain.entities.User
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.domain.useCases.UiState
import de.rogallab.android.ui.composables.ShowErrorMessage
import de.rogallab.android.ui.theme.AppTheme
import de.rogallab.android.ui.navigation.NavScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactsListScreenSwipe(
   navController: NavController,
   viewModel: UsersViewModel = hiltViewModel(),
) {

   val tag = "ok>ContactsListScreen ."
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
               if (uiState.data is List<*>) items = uiState.data as MutableList<User>
               LazyColumn(state = rememberLazyListState()) {
                  items(items = items) { contact ->
                     // var personRemoved: Person?
                     val dismissState = rememberDismissState(
                        confirmStateChange = {
                           if (it == DismissValue.DismissedToEnd) {
                              LogFun("ok>SwipeToDismiss().", "-> Edit")
                              navController.navigate(NavScreen.UserDetail.route + "/${contact.id.toString()}")
                              return@rememberDismissState true
                           } else if (it == DismissValue.DismissedToStart) {
                              LogFun("ok>SwipeToDismiss().", "-> Delete")
                              // personRemoved = person
                              viewModel.remove(contact.id)
                              navController.navigate(NavScreen.UsersList.route)
                              return@rememberDismissState true
                           }
                           return@rememberDismissState false
                        }
                     )

                     SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier.padding(vertical = 4.dp),
                        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                        dismissThresholds = { direction ->
                           FractionalThreshold(
                              if (direction == DismissDirection.StartToEnd) 0.25f else 0.25f
                           )
                        },
                        background = {
                           val direction = dismissState.dismissDirection ?: return@SwipeToDismiss

                           val colorBox by animateColorAsState(
                              when (dismissState.targetValue) {
                                 DismissValue.Default -> MaterialTheme.colors.secondary
                                 DismissValue.DismissedToEnd -> Color.Green
                                 DismissValue.DismissedToStart -> Color.Red
                              }
                           )
                           val colorIcon by animateColorAsState(
                              when (dismissState.targetValue) {
                                 DismissValue.Default -> Color.Black
                                 DismissValue.DismissedToEnd -> Color.DarkGray
                                 DismissValue.DismissedToStart -> Color.White
                              }
                           )
                           val alignment = when (direction) {
                              DismissDirection.StartToEnd -> Alignment.CenterStart
                              DismissDirection.EndToStart -> Alignment.CenterEnd
                           }
                           val icon = when (direction) {
                              DismissDirection.StartToEnd -> Icons.Default.Edit
                              DismissDirection.EndToStart -> Icons.Default.Delete
                           }
                           val scale by animateFloatAsState(
                              if (dismissState.targetValue == DismissValue.Default) 1.25f else 2.0f
                           )

                           Box(
                              Modifier
                                 .fillMaxSize()
                                 .background(colorBox)
                                 .padding(horizontal = 20.dp),
                              contentAlignment = alignment
                           ) {
                              Icon(
                                 icon,
                                 contentDescription = "Localized description",
                                 modifier = Modifier
                                    .scale(scale),
                                 tint = colorIcon
                              )
                           }
                        },
                        dismissContent = {
                           Column {
                              ContactCard(
                                 user = contact,
                                 elevation = animateDpAsState(
                                    if (dismissState.dismissDirection != null) 8.dp else 0.dp
                                 ).value,
                              ) {// onClick
                                 navController.navigate(NavScreen.UserDetail.route + "/${contact.id.toString()}")
                              }

                              Divider()
                           }
                        }
                     )
                  }
               }
            }
         }
      }
   }
}
