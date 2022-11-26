package de.rogallab.android.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.rogallab.android.AppStart
import de.rogallab.android.domain.IUsersDao
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.useCases.*
import de.rogallab.android.domain.utilities.LoggerImpl
import de.rogallab.android.domain.utilities.Seed
import de.rogallab.android.data.database.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import javax.inject.Singleton


// https://medium.com/androiddevelopers/dependency-injection-on-android-with-hilt-67b6031e62d


@Module
@InstallIn(SingletonComponent::class)
object AppModulesSingleton {

   const val tag = "ok>AppModulesSingleton."

   @Singleton
   @Provides
   fun provideLogger(
   ): ILogger {
      val logger = LoggerImpl  // = kotlin object (singleton=
      logger.logInformation(tag, "provideLogger()")
      return logger
   }

   @Singleton
   @Provides
   fun provideContext(
      application: Application, // provided by Hilt
      logger: ILogger
   ): Context {
      logger.logInformation(tag, "provideContext()")
      return application.applicationContext
   }

   @Singleton
   @Provides
   fun provideDispatcher(
      logger: ILogger
   ): CoroutineDispatcher {
      logger.logInformation(tag, "provideDispatcher()")
      return Dispatchers.IO
   }

   @Provides
   @Singleton
   fun provideAppDatabase(
      application: Application, // provided by Hilt
      logger: ILogger
   ): AppDatabase {
      logger.logInformation(tag, "provideAppDatabase()")
      return Room.databaseBuilder(
         application.applicationContext,
         AppDatabase::class.java,
         AppStart.database_name
      ).fallbackToDestructiveMigration()
         .build()
   }

   @Provides
   @Singleton
   fun provideContactsDao(
      database: AppDatabase,
      logger: ILogger
   ): IUsersDao {
      logger.logInformation(tag, "provideContactsDao()")
      return database.createContactsDao()
   }

//   @Provides
//   @Singleton
//   fun provideContactsRepository(
//      contactsDao: IUsersDao,
//      dispatcher: CoroutineDispatcher = Dispatchers.IO,
//      logger: ILogger
//   ): IUsersRepository {
//      logger.logInformation(tag, "provideContactsRepository()")
//      return UsersRepositoryImpl(
//         contactsDao,
//         dispatcher,
//         logger
//      )
//   }

//   @Provides
//   @Singleton
//   fun provideContactUseCases(
//      repository: IUsersRepository,
//      seed: Seed,
//      logger: ILogger
//   ): ContactUseCases {
//      logger.logInformation(tag, "provideContactUseCases()")
//      return ContactUseCases(
//         getContactById = GetContactById(repository),
//         getContacts = GetContacts(repository, seed),
//         registerUser = RegisterUser(repository),
//         updateUser = UpdateUser(repository),
//         removeUser = RemoveUser(repository),
//      )
//   }

   @Provides
   @Singleton
   fun provideSeed(
      context: Context,
      logger: ILogger
   ): Seed {
      logger.logInformation(tag, "provideSeed()")
      return Seed(context)
   }
}