package de.rogallab.android.ui.users

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import de.rogallab.android.R

@Composable
fun InputNameMailPhone(
   firstName: String,                    // State ↓
   onFirstNameChange: (String) -> Unit,  // Event ↑
   lastName: String,                     // State ↓
   onLastNameChange: (String) -> Unit,   // Event ↑
   email: String?,                       // State ↓
   onEmailChange: (String) -> Unit,      // Event ↑
   phone: String?,                       // State ↓
   onPhoneChange: (String) -> Unit       // Event ↑
) {

   OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      value = firstName,
      onValueChange = { onFirstNameChange(it) },
      label = { Text(text = stringResource(R.string.firstName)) },
      textStyle = MaterialTheme.typography.body1,
      leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = "First Name") },
      singleLine = true,
   )

   OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      value = lastName,
      onValueChange = { onLastNameChange(it) },
      label = { Text(text = stringResource(R.string.lastName)) },
      textStyle = MaterialTheme.typography.body1,
      leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = "Last Name") },
      singleLine = true,
   )

   OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      value = email ?: "",
      onValueChange = { onEmailChange(it) },
      label = { Text(text = stringResource(R.string.email)) },
      textStyle = MaterialTheme.typography.body1,
      keyboardOptions = KeyboardOptions(
         keyboardType = KeyboardType.Email
      ),
      leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = "Email") },
      singleLine = true
   )

   OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      value = phone ?: "",
      onValueChange = { onPhoneChange(it) },
      label = { Text(text = stringResource(R.string.phone)) },
      textStyle = MaterialTheme.typography.body1,
      leadingIcon = { Icon(imageVector = Icons.Outlined.Phone, contentDescription = "Phone") },
      keyboardOptions = KeyboardOptions(
         keyboardType = KeyboardType.Phone
      ),
      singleLine = true
   )
}

