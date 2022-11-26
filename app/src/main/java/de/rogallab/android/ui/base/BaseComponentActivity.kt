package de.rogallab.android.ui.base

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import de.rogallab.android.domain.ILogger
import javax.inject.Inject

open class BaseComponentActivity(
   private val _tag: String
) : ComponentActivity() {

   // property injection with Dagger/Hilt
   @Inject
   lateinit var _logger: ILogger

   // Start of full lifetime, Activity is first created
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      _logger.logDebug(_tag, "onCreate()")
   }

   // Start of visible lifetime, Activity is visible
   override fun onStart() {
      super.onStart()
      _logger.logDebug(_tag, "onStart()")
   }

   // Start of the active lifetime, Activity interacts with the user
   override fun onResume() {
      super.onResume()
      _logger.logDebug(_tag, "onResume()")
   }

   // End of active lifetime, User is leaving activity
   override fun onPause() {
      _logger.logDebug(_tag, "onPause()")
      super.onPause()
   }

   // End of visible lifetime, Activity is no longer visible
   override fun onStop() {
      _logger.logDebug(_tag, "onStop()")
      super.onStop()
   }

   // End of full lifetime, Called before the activity is destroyed.
   override fun onDestroy() {
      _logger.logDebug(_tag, "onPause()")
      super.onDestroy()
   }

   // Activity is restarted
   override fun onRestart() {
      super.onRestart()
      _logger.logDebug(_tag, "onRestart()")
   }

   // override save/restore state methods -------------------------------------
   // Save instance state: invoked when the activity may be temporarily destroyed,
   override fun onSaveInstanceState(savedStateBundle: Bundle) {
      super.onSaveInstanceState(savedStateBundle)
      _logger.logDebug(_tag, "onSaveInstanceState()")
   }

   override fun onRestoreInstanceState(savedInstanceState: Bundle) {
      super.onRestoreInstanceState(savedInstanceState)
      _logger.logDebug(_tag, "onRestoreInstanceState()")
   }

   override fun onConfigurationChanged(newConfig: Configuration) {
      super.onConfigurationChanged(newConfig)

      // Checks the orientation of the screen
      if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
         _logger.logDebug(_tag, "landscape")
      } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
         _logger.logDebug(_tag, "portrait")
      }
   }

   override fun onWindowFocusChanged(hasFocus: Boolean) {
      _logger.logDebug(_tag, "onWindowFocusChanged() $hasFocus")
      super.onWindowFocusChanged(hasFocus)
   }

   override fun onBackPressed() {
      Log.d(_tag, "onBackPressed()")
      super.onBackPressed()
   }

   override fun onTrimMemory(level: Int) {
      super.onTrimMemory(level)
      _logger.logDebug(_tag, "onTrimMemory() $level")
      if (level == TRIM_MEMORY_RUNNING_MODERATE)_logger.logDebug(_tag, "TRIM_MEMORY_RUNNING_MODERATE")
      if (level == TRIM_MEMORY_RUNNING_LOW) _logger.logDebug(_tag, "TRIM_MEMORY_RUNNING_LOW")
      if (level == TRIM_MEMORY_RUNNING_CRITICAL) _logger.logDebug(_tag, "TRIM_MEMORY_RUNNING_CRITICAL")
      if (level == TRIM_MEMORY_BACKGROUND) _logger.logDebug(_tag, "TRIM_MEMORY_BACKGROUND")
      if (level == TRIM_MEMORY_COMPLETE) _logger.logDebug(_tag, "TRIM_MEMORY_COMPLETE")
      if (level == TRIM_MEMORY_UI_HIDDEN) _logger.logDebug(_tag, "TRIM_MEMORY_UI_HIDDEN")
      if (level == TRIM_MEMORY_MODERATE) _logger.logDebug(_tag, "TRIM_MEMORY_MODERATE")
   }
}