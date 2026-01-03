package edu.iesam.bikerly.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MotorbikeDao {

    @Query("SELECT * FROM motorbikeentity")
    fun getAll(): List<MotorbikeEntity>

    @Query("SELECT * FROM motorbikeentity WHERE id IN (:motorbikeId)")
    fun getById(motorbikeId: Int): MotorbikeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(vararg motorbikes: MotorbikeEntity)

    @Delete
    fun delete(motorbike: MotorbikeEntity)
}