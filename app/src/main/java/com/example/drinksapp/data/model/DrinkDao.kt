package com.example.drinksapp.data.model

import androidx.room.*

@Dao
interface DrinkDao {

    @Query("SELECT *  FROM drinkEntity")
    suspend fun getAllDrinks():List<DrinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(drink:DrinkEntity)

    @Delete
    suspend fun deleteDrink(drink: DrinkEntity)

}