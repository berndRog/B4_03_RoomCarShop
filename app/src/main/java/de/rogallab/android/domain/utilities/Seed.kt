package de.rogallab.android.domain.utilities

import android.content.Context
import android.graphics.BitmapFactory
import de.rogallab.android.R
import de.rogallab.android.domain.entities.User
import de.rogallab.android.data.io.writeImageToInternalStorage
import java.util.*

class Seed(
   context: Context
) {

   private var _imageNumber = 1;
   val contacts: MutableList<User> = mutableListOf()

   val contact1 = User(
      firstName = "Albrecht",
      lastName = "Meißner",
      email = "al.meissner@ostfalia.de",
      phone = "05826 988 61160",
      imagePath = getImagePath(context, R.drawable.man_1, _imageNumber++),
      id = UUID.fromString("10000000-0000-0000-0000-000000000000")
   ).also { contacts.add(it) }

   val contact2 = User(
      firstName = "Arne",
      lastName = "Noyer",
      email = "arne.noyer@ostfalia.de",
      phone = "05826 988 61190",
      imagePath = getImagePath(context, R.drawable.man_2, _imageNumber++)
   ).also { contacts.add(it) }

   val contact3 = User(
      firstName = "Bernd",
      lastName = "Rogalla",
      email = "b-u.rogalla@ostfalia.de",
      phone = "05826 988 61180",
      imagePath = getImagePath(context, R.drawable.man_3, _imageNumber++)
   ).also { contacts.add(it) }

   val contact4 = User(
      firstName = "Jorin",
      lastName = "Kleimann",
      email = "j.kleimann@ostfalia.de",
      phone = "05826 988 61550",
      imagePath = getImagePath(context, R.drawable.man_4, _imageNumber++)
   ).also { contacts.add(it) }

   val contact5 = User(
      firstName = "Christian",
      lastName = "Hollmann",
      email = "ch.hollmann@ostfalia.de",
      phone = "05826 988 61610",
      imagePath = getImagePath(context, R.drawable.man_5, _imageNumber++)
   ).also { contacts.add(it) }

   val contact6 = User(
      firstName = "Frank",
      lastName = "Dziembowski",
      email = "f.Dziembowski@ostfalia.de",
      phone = "05826 988 61420",
      imagePath = getImagePath(context, R.drawable.man_6, _imageNumber++)
   ).also { contacts.add(it) }


   val contact7 = User(
      firstName = "Uwe",
      lastName = "Kadelka",
      email = "uwe.kadelka@ostfalia.de",
   ).also { contacts.add(it) }

   val contact8 = User(
      firstName = "Gesa",
      lastName = "Wüncke",
      email = "g.wuencke@ostfalia.de",
      imagePath = getImagePath(context, R.drawable.woman_1, _imageNumber++)
   ).also { contacts.add(it) }

   val contact9 = User(
      firstName = "Achim",
      lastName = "Arndt",
      email = "a.arndt@t-online.de",
      imagePath = getImagePath(context, R.drawable.man_7, _imageNumber++)
   ).also { contacts.add(it) }

   val contact10 = User(
      firstName = "Beate",
      lastName = "Bauer",
      email = "b.bauer@gmx.de",
      imagePath = getImagePath(context, R.drawable.woman_2, _imageNumber++)
   ).also { contacts.add(it) }

   val contact11 = User(
      firstName = "Christine",
      lastName = "Cornelsen",
      email = "c.cornelsen@gmail.com",
      imagePath = getImagePath(context, R.drawable.woman_3, _imageNumber++)
   ).also { contacts.add(it) }


}

fun getImagePath(
   context: Context,
   resDrawable: Int,
   number: Int
): String? {

   val result = BitmapFactory.decodeResource(context.resources, resDrawable)
      ?.let { bitmap ->
         writeImageToInternalStorage(context = context, bitmap = bitmap) // ^let
      }
      ?: run {
         val directory = context.getDir("images", Context.MODE_PRIVATE)
         """$directory/${number.toString().padStart(10, '0')}.jpg""" // ^run
      }
   return result
}