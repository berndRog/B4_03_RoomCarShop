package de.rogallab.android.domain

interface ILogger {
   fun logError(tag: String, msg: String)
   fun logWarning(tag: String, msg: String)
   fun logInformation(tag: String, msg: String)
   fun logDebug(tag: String, msg: String)
}