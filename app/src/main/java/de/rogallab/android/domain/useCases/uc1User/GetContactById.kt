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

class GetContactById @Inject constructor(
   private val _repository: IUsersRepository,
   private val _dispatcher: CoroutineDispatcher,
   private val _logger: ILogger
) {
   init {
      _logger.logInformation(tag, "instance created")
   }

   operator fun invoke(id: UUID): Flow<UiState> = flow {
      _logger.logDebug(tag, "emit loading")
      emit(UiState.Loading)
      delay(1000)

      val contact = _repository.getById(id)
      _logger.logDebug(tag, "emit data")
      emit(UiState.Success(data = contact))
   } .catch {
      _logger.logDebug(tag, "emit error")
      emit(UiState.Error(message = "${it.localizedMessage}"))
   } .flowOn(_dispatcher)

   companion object {
      private val tag: String = "ok>GetContactbyIdUC   ."
   }
}