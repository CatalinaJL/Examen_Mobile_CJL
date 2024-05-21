package com.cjl.examen_mobile_cjl.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface LugarDAO {

    @Query("SELECT * FROM Lugar")
    fun getAll(): List<Lugar>

    @Insert
    fun insert(lugar:Lugar):Long

    @Update
    fun update(lugar: Lugar)

    @Delete
    fun delete(lugar:Lugar)

    @Query("SELECT count(*) FROM Lugar")
    fun countRegisters():Int

}