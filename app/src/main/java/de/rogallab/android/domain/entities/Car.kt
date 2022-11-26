package de.rogallab.android.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "car")
data class Car(
   @PrimaryKey
   var make: String = "",
   var model: String = "",
   var price: Double = 0.0,
   var firstReg: Int = 1900,
   @ColumnInfo(name = "image_uri")
   var imagePath: String? = null,
   @ColumnInfo(name = "pk_car")
   val id: UUID = UUID.randomUUID(),
   @ColumnInfo(name = "fk_user")
   val userId: UUID? = null
)