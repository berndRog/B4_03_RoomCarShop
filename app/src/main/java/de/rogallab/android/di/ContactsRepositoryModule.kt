package de.rogallab.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.rogallab.android.domain.IUsersRepository
import de.rogallab.android.data.repositories.UsersRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {
   @Binds
   @Singleton
   abstract fun bindContactsRepository(
      contactsRepositoryImpl: UsersRepositoryImpl
   ): IUsersRepository
}