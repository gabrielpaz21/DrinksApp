package com.example.drinksapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.drinksapp.data.model.DrinkDao
import com.example.drinksapp.data.model.DrinkEntity

@Database(entities = [DrinkEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun drinkDao(): DrinkDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "drinkEntity"
            ).build()
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }


}