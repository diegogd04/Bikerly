package edu.iesam.bikerly.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.iesam.bikerly.data.local.room.MotorbikeDao
import edu.iesam.bikerly.data.local.room.MotorbikeEntity

@Database(entities = [MotorbikeEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun motorbikeDao(): MotorbikeDao
}