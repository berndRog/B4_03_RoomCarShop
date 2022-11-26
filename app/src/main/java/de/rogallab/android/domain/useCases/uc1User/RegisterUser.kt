package de.rogallab.android.domain.useCases.uc1User

import de.rogallab.android.domain.IUsersRepository
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.entities.User
import de.rogallab.android.domain.useCases.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterUser @Inject constructor(
   private val _usersRepository: IUsersRepository,
   private val _dispatcher: CoroutineDispatcher,
   private val _logger: ILogger
) {
   init { _logger.logInformation(tag,"instance created") }

   operator fun invoke(contact: User): Flow<UiState> = flow {
      _logger.logDebug(tag, "emit loading")
      emit(UiState.Loading)

      _usersRepository.add(contact)
      _logger.logDebug(tag, "emit data")
      emit(UiState.Success(data = contact))

   }.catch {
      _logger.logDebug(tag, "emit error")
      emit(UiState.Error(message = "${it.localizedMessage}"))

   } .flowOn(_dispatcher)

   companion object {          //12345678901234567890123
      private val tag: String = "ok>RegisterUserUC     ."
   }
}