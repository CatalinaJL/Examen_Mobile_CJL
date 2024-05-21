package com.cjl.examen_mobile_cjl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Lugar::class], version=1)
abstract class AppDB :RoomDatabase() {

    abstract fun lugarDao(): LugarDAO


    companion object {

        @Volatile
        private var BASE_DATOS: AppDB? = null

        fun getInstance(contexto: Context): AppDB {
            return BASE_DATOS ?: synchronized(this) {
                Room.databaseBuilder(
                    contexto.applicationContext,
                    AppDB::class.java,
                    "AppDB.bd"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { BASE_DATOS = it }

            }
        }
    }
}