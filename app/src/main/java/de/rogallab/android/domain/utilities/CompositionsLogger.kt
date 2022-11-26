/**/package de.rogallab.android.domain.utilities

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import de.rogallab.android.BuildConfig

// https://www.jetpackcompose.app/articles/how-can-I-debug-recompositions-in-jetpack-compose
@Composable
fun LogComp(tag: String, msg: String) {
   if (BuildConfig.DEBUG) {
      val ref = remember { Ref(1) }
      SideEffect { ref.value++ }
      val message = "Comp ${ref.value}: $msg"
      Log.d(tag, formatMessage(message))
   }
}

fun LogFun(tag: String, message: String) {
   if (BuildConfig.DEBUG) {
      Log.d(tag, formatMessage(message))
   }
}

private fun formatMessage(message:String) =
   String.format("%-50s %s", message, Thread.currentThread().toString())

class Ref(var value: Int)