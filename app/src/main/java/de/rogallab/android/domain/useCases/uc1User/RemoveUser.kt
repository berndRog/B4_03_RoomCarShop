package de.rogallab.android.domain.useCases.uc1User

import de.rogallab.android.domain.IUsersRepository
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.useCases.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

// https://medium.com/adidoescode/android-arch-exploration-mvvm-flow-usecases-with-a-ui-gate-combine-use-cases-output-to-541abc6a7b8c


class RemoveUser @Inject constructor(
   private val _usersRepository: IUsersRepository,
   private val _dispatcher: CoroutineDispatcher,
   private val _logger: ILogger
) {
   init { _logger.logInformation(tag, "instance created") }

   operator fun invoke(id: UUID): Flow<UiState> = flow {
      emit(UiState.Loading)
      _usersRepository.getById(id)?.let {
         _usersRepository.remove(it)
         emit(UiState.Success(data = it))
      }
      //else
      ?: run {
         emit(UiState.Error(message = "User with id $id not found"))
      }
   }  .catch {
         _logger.logDebug(tag, "emit error")
         emit(UiState.Error(message = "${it.localizedMessage}"))
   }  .flowOn(_dispatcher)

   companion object {
      private const val tag: String = "ok>RemoveUserUC       ."
   }
}