package de.rogallab.android.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import de.rogallab.android.ui.base.BaseComponentActivity
import de.rogallab.android.ui.theme.AppTheme
import de.rogallab.android.ui.navigation.AppNavHost

@AndroidEntryPoint
class MainActivity : BaseComponentActivity(_tag) {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      setContent {
         AppTheme() {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colors.background
            ) {
               AppNavHost()
            }
         }
      }
   }
   companion object {
      private val _tag = "ok>MainActivity       ."
   }
}