package de.rogallab.android.domain.useCases.uc1User

import de.rogallab.android.domain.IUc1User
import javax.inject.Inject

data class Uc1UserImpl @Inject constructor(
   override val getContactById: GetContactById,
   override val getContacts: GetContacts,
   override val registerUser: RegisterUser,
   override val updateUser: UpdateUser,
   override val removeUser: RemoveUser
) : IUc1User