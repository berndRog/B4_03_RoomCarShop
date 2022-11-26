package de.rogallab.android.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.ui.users.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppNavHost(
) {
   val navHostController: NavHostController = rememberNavController()
   val viewModel: UsersViewModel = hiltViewModel()

// https://medium.com/androiddevelopers/animations-in-navigation-compose-36d48870776b

   NavHost(
      navController = navHostController,
      startDestination = NavScreen.UsersList.route
   ) {
      composable(
         route = NavScreen.UsersList.route,
      ) {
         LogComp("ok>AppNavHost         .", "ContactsListScreen()")
         UsersListScreen(
            navController = navHostController,
            viewModel = viewModel
         )
      }

      composable(
         route = NavScreen.UserInput.route,
      ) {
         LogComp("ok>AppNavHost         .", "ContactInputScreen()")
         UserInputScreen(
            navController = navHostController,
            viewModel = viewModel
         )
      }

      composable(
         route = NavScreen.UserDetail.route+"/{contactId}",
         arguments = listOf(navArgument("contactId") { type = NavType.StringType})
      ) { backStackEntry ->
         LogComp("ok>AppNavHost         .", "ContactDetailScreen()")
         UserDetailScreen(
            navController = navHostController,
            viewModel = viewModel,
            contactId = backStackEntry.arguments?.getString("contactId")
         )
      }
   }
}