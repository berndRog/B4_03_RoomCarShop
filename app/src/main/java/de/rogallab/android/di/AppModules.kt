package de.rogallab.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import de.rogallab.android.domain.IUc1User
import de.rogallab.android.domain.useCases.*
import de.rogallab.android.domain.useCases.uc1User.Uc1UserImpl

@InstallIn(ActivityComponent::class)
@Module
abstract class AppModules {

   @ActivityScoped
   @Binds
   abstract fun bindContactUseCases(
      contactsUseCases: Uc1UserImpl
   ): IUc1User

}

//@InstallIn(ActivityComponent::class)
//@Module
//abstract class FreeActivityModule {
//
//   @ActivityScoped
//   @Binds
//   @FreeModule
//   abstract fun bindFreeModule(impl: FreeActivity): YourInterface
//}