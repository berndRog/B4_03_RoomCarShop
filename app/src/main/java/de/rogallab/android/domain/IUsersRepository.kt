package de.rogallab.android.domain
import de.rogallab.android.domain.entities.User
import kotlinx.coroutines.flow.Flow
import java.util.*

interface IUsersRepository {

   fun getAll(): Flow<MutableList<User>>
   suspend fun getById(id: UUID): User?
   suspend fun getByPhone(phone: String): User?

   suspend fun add(contact: User)
   suspend fun addAll(contacts: List<User>)
   suspend fun update(contact: User)
   suspend fun remove(contact: User)
   suspend fun removeAll()

   suspend fun count(): Int

}