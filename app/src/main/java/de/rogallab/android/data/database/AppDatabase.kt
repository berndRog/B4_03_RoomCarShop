package de.rogallab.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.rogallab.android.AppStart
import de.rogallab.android.domain.IUsersDao
import de.rogallab.android.domain.entities.Car
import de.rogallab.android.domain.entities.User

@Database(entities = [User::class, Car::class],
          version = AppStart.database_version,
          exportSchema = false)

abstract class AppDatabase: RoomDatabase() {

   // The database exposes DAOs through an abstract "getter" method for each @Dao.
   abstract fun createContactsDao(): IUsersDao

}