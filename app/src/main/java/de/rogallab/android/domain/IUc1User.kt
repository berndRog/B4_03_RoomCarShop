package de.rogallab.android.domain
import de.rogallab.android.domain.useCases.uc1User.*

interface IUc1User {
   // properties (getter)
   val getContactById: GetContactById
   val getContacts: GetContacts
   val registerUser: RegisterUser
   val updateUser: UpdateUser
   val removeUser: RemoveUser
}