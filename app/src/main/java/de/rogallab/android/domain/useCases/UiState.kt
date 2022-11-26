package de.rogallab.android.domain.useCases

sealed class UiState {
   object Loading : UiState()
   object Retrying : UiState()
   data class Success<T>(val data: T) : UiState()
   data class Error(val message: String) : UiState()
}




