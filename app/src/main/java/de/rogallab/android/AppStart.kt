package de.rogallab.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.utilities.toDateTimeString
import java.time.LocalDateTime
import javax.inject.Inject

@HiltAndroidApp
class AppStart : Application() {

   // property injection with Dagger/Hilt
   @Inject
   lateinit var _logger: ILogger

   override fun onCreate() {
      super.onCreate()

      val dateTimeStart = LocalDateTime.now()
      val maxMemory = (Runtime.getRuntime().maxMemory() / 1024 ).toInt()

      _logger.logDebug(tag, "onCreate() maxMemory ${toDateTimeString(dateTimeStart)}")
      _logger.logDebug(tag, "onCreate() maxMemory $maxMemory kB")

   }

   companion object {
      //                       123456789012345678901234567890
      private const val tag = "ok>AppStart           ."
      const val database_name:    String = "B4_01_RoomContacts.db"
      const val database_version: Int    = 1
      const val URL:              String = "http://10.0.2.2:5000/api/v1.0/"
   }
}