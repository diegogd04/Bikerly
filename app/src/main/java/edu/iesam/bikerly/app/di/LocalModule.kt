package edu.iesam.bikerly.app.di

import android.content.Context
import androidx.room.Room
import edu.iesam.bikerly.app.db.AppDataBase
import edu.iesam.bikerly.data.local.room.MotorbikeDao
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("edu.iesam.bikerly")
class LocalModule {

    @Single
    fun provideDataBase(context: Context): AppDataBase {
        val db = Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "bikerly-database"
        )

        return db.build()
    }

    @Single
    fun provideMotorbikeDao(db: AppDataBase): MotorbikeDao {
        return db.motorbikeDao()
    }
}