package de.rogallab.android.data.repositories

import de.rogallab.android.domain.*
import de.rogallab.android.domain.entities.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
   private val _contactsDao: IUsersDao,
   private val _dispatcher: CoroutineDispatcher = Dispatchers.IO,
   private val _logger: ILogger
) : IUsersRepository {

   override fun getAll(): Flow<MutableList<User>> {
      _logger.logDebug(_tag, "getAll()")
      return _contactsDao.selectAll()
   }

   // override suspend fun getById(id: UUID): User? =
//    withContext(_dispatcher) {
   override suspend fun getById(id: UUID): User? {
      _logger.logDebug(_tag, "getById()")
      return _contactsDao.selectById(id)
   }

   override suspend fun getByPhone(phone: String): User? {
      _logger.logDebug(_tag, "getByPhone()")
      return _contactsDao.selectByPhone(phone)
   }

   override suspend fun add(contact: User) {
      _logger.logDebug(_tag, "add()")
      _contactsDao.insert(contact)
   }

   override suspend fun addAll(contacts: List<User>) {
      _logger.logDebug(_tag, "addAll()")
      _contactsDao.insertAll(contacts)
   }

   override suspend fun update(contact: User) {
      _logger.logDebug(_tag, "update()")
      _contactsDao.update(contact)
   }

   override suspend fun remove(contact: User) {
      _logger.logDebug(_tag, "remove()")
      _contactsDao.delete(contact)
   }

   override suspend fun removeAll() {
      _logger.logDebug(_tag, "removeAll()")
      _contactsDao.deleteAll()
   }

   override suspend fun count(): Int {
      _logger.logDebug(_tag, "count()")
      return _contactsDao.count()
   }

   companion object {
      private val _tag: String = "ok>ContactsRepository ."
   }
}