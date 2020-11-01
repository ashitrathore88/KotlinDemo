package com.ar.cartonclouddemo.room

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ar.cartonclouddemo.model.WeatherData

@Database(entities = [WeatherData::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {


    abstract fun weatherDataDao(): WeatherDataDao

    companion object{
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
           return instance ?: synchronized(this){ instance ?: buildDatabase(context).also { instance = it}}
        }


        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext,AppDatabase::class.java,"Weather")
                .build()

    }
}