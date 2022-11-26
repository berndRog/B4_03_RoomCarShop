package de.rogallab.android.domain.utilities
import android.util.Log
import de.rogallab.android.domain.ILogger

object LoggerImpl: ILogger {

   override fun logError(tag: String, msg: String) {
      Log.e(tag,formatMessage(msg))
   }

   override fun logWarning(tag: String, msg: String) {
      Log.w(tag,formatMessage(msg))
   }

   override fun logInformation(tag: String, msg: String) {
      Log.i(tag,formatMessage(msg))
   }

   override fun logDebug(tag: String, msg: String) {
      Log.d(tag,formatMessage(msg))
   }

   private fun formatMessage(message:String) =
      String.format("%-50s %s", message, Thread.currentThread().toString())

}