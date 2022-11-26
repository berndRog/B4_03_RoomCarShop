package de.rogallab.android.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user")
data class User(
   var firstName: String,
   var lastName: String,
   var email: String? = null,
   var phone:String? = null,
   var userName: String = "",
   var password: String = "",
   var salt: String = "",
   @ColumnInfo(name = "image_uri")
   var imagePath: String? = null,
   @PrimaryKey
   var id: UUID = UUID.randomUUID()
)
