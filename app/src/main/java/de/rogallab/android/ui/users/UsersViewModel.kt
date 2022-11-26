package de.rogallab.android.ui.users

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.entities.User
import de.rogallab.android.domain.useCases.uc1User.Uc1UserImpl
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.domain.useCases.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
   private val _uc1User: Uc1UserImpl,
   private val _logger: ILogger
) : ViewModel() {

   // State for Input
   var id: UUID = UUID.randomUUID()

   // mutable states ContactScreen ...
   var firstName: String by mutableStateOf(value = "")
      private set
   fun onFirstNameChange(value: String) {
      firstName = value
   }

   var lastName: String by mutableStateOf(value = "")
      private set
   fun onLastNameChange(value: String) {
      lastName = value
   }

   var email: String? by mutableStateOf(value = null)
      private set
   fun onEmailChange(value: String) {
      email = value
   }

   var phone: String? by mutableStateOf(value = null)
      private set
   fun onPhoneChange(value: String?) {
      phone = value
   }

   var imagePath: String? by mutableStateOf(value = null)
      private set
   fun onImagePathChange(value: String?) {
      imagePath = value
   }

   private val _mutableStateOfUiState = mutableStateOf<UiState>(UiState.Loading)
   val stateOfUiState: State<UiState>
      get() = _mutableStateOfUiState

   // callback errorhandling (dismiss/action)
   fun onError(errorMessage: String) {
      _mutableStateOfUiState.value = UiState.Error(message = errorMessage)
   }
   fun onErrorDismiss() { LogFun(_tag, "onErrorReject()")   }
   fun onErrorAction()  { LogFun(_tag, "onErrorAction()")   }

   init {
      readAll(true)
   }

   fun readAll(shouldSeed: Boolean) {
      _logger.logDebug(_tag, "readAll()")
      _uc1User.getContacts(shouldSeed).onEach { uiState ->
         _mutableStateOfUiState.value = uiState
      }.launchIn(viewModelScope)
   }

   fun readById(id: UUID) {
      _logger.logDebug(_tag, "readById() ${firstName} ${lastName}")
      _uc1User.getContactById(id).onEach { uiState ->
         _mutableStateOfUiState.value = uiState
         // set state, i.e. firstName, lastName ...
         if(uiState is UiState.Success<*>) {
            if(uiState.data is User) getState(uiState.data)
         }
      }.launchIn(viewModelScope)
   }

   fun add() {
      _logger.logDebug(_tag, "add() ${firstName} ${lastName}")
      val contact = User(firstName, lastName, email, phone, imagePath, id)
      _uc1User.registerUser(contact).onEach { uiState ->
         _mutableStateOfUiState.value = uiState
      }
      clearState()
   }

   fun update(
      contactId: UUID,
      firstName: String? = null,
      lastName: String? = null,
      email: String? = null,
      phone: String? = null,
      imagePath: String? = null
   ) {
      _logger.logDebug(_tag, "update() ${firstName} ${lastName}")
      _uc1User.updateUser(
         contactId, firstName, lastName, email, phone, imagePath).onEach { uiState ->
            _mutableStateOfUiState.value = uiState
      }
      clearState()
   }

   fun remove(id: UUID) {
      _logger.logDebug(_tag, "remove() ${firstName} ${lastName}")
      _uc1User.removeUser(id).onEach { uiState ->
         _mutableStateOfUiState.value = uiState
      }
   }

   override fun onCleared() {
      Log.d(_tag, "onCleared()")
      super.onCleared()
   }

   private fun clearState() {
      id = UUID.fromString("00000000-0000-0000-0000-000000000000")
      firstName = ""
      lastName = ""
      email = null
      phone = null
      imagePath = null
   }

   private fun getState(contact: User) {
      id = contact.id
      firstName = contact.firstName
      lastName = contact.lastName
      email = contact.email
      phone = contact.phone
      imagePath = contact.imagePath
   }


   companion object {
      private val _tag: String = "ok>UsersViewModel   ."
   }
}