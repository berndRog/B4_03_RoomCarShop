package de.rogallab.android.domain.useCases.uc1User

import de.rogallab.android.domain.IUsersRepository
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.entities.User
import de.rogallab.android.domain.utilities.Seed
import de.rogallab.android.domain.useCases.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetContacts @Inject constructor(
   private val _repository: IUsersRepository,
   private val _dispatcher: CoroutineDispatcher,
   private val _seed: Seed,
   private val _logger: ILogger
) {
   init { _logger.logInformation(tag,"instance created") }

   operator fun invoke(
      shouldSeed:Boolean = false
   ): Flow<UiState>  = flow {

      _logger.logDebug(tag,"emit loading")
      emit (UiState.Loading)
      delay(1000)

      // if database is empty and shouldSeed is true
      // then seed the database with default values
      val count = _repository.count()
      if(count == 0 && shouldSeed) {
         val contacts = _seed.contacts
         _repository.addAll(contacts)
      }
      _repository.getAll().collect { contacts: MutableList<User> ->
         _logger.logDebug(tag,"emit data")
         emit(UiState.Success(data = contacts))
      }
   } .catch{
      _logger.logDebug(tag,"emit error")
      emit(UiState.Error(message = "${it.localizedMessage}"))
   } .flowOn(_dispatcher)

   companion object {
      private val tag: String = "ok>GetContactUC       ."
   }
}