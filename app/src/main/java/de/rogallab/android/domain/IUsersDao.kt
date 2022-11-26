package de.rogallab.android.domain
import androidx.room.*
import de.rogallab.android.domain.entities.User
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface IUsersDao {

   @Query("SELECT * FROM user ORDER BY lastName ASC")
   fun selectAll(): Flow<MutableList<User>>

   @Query("SELECT * FROM user WHERE id = :id")
   suspend  fun selectById(id: UUID): User?

   @Query("SELECT * FROM user WHERE userName = :userName")
   suspend fun selectByPhone(userName: String): User?

   @Query("SELECT COUNT(*) FROM user")
   suspend  fun count(): Int

   @Insert(onConflict = OnConflictStrategy.ABORT)
   suspend fun insert(contact: User)

   @Insert(onConflict = OnConflictStrategy.ABORT)
   suspend fun insertAll(contacts: List<User>)

   @Update
   suspend fun update(contact: User)

   @Delete
   suspend fun delete(contact: User)

   @Query("DELETE FROM user")
   suspend fun deleteAll()

}