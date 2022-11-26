package de.rogallab.android.domain.useCases.uc1User

import de.rogallab.android.domain.IUsersRepository
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.useCases.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class UpdateUser @Inject constructor(
   private val _usersRepository: IUsersRepository,
   private val _dispatcher: CoroutineDispatcher,
   private val _logger: ILogger
) {
   init {
      _logger.logInformation(tag, "instance created")
   }

   operator fun invoke(
      id: UUID,
      firstName: String? = null,
      lastName: String? = null,
      email: String? = null,
      phone: String? = null,
      imagePath: String? = null
   ): Flow<UiState> = flow {

      emit(UiState.Loading)

      // update when the contact with given id is found
      val user = _usersRepository.getById(id)?.apply {
         // and each attribute is not null
         firstName?.let { this.firstName = it }
         lastName?.let { this.firstName = it }
         email?.let { this.firstName = it }
         phone?.let { this.firstName = it }
         imagePath?.let { this.imagePath = it }
      }
      //if( contact != null)
      user?.let {
         emit(UiState.Success(data = it))
      //else
      } ?: run {
          emit(UiState.Error(message = "User with id $id not found"))
      }
   }.catch {
      _logger.logDebug(tag, "emit error")
      emit(UiState.Error(message = "${it.localizedMessage}"))
   }.flowOn(_dispatcher)

   companion object {
      private const val tag: String = "ok>UpdateUserUC       ."
   }
}