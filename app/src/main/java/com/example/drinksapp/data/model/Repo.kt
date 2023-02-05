package com.example.drinksapp.data.model

import com.example.drinksapp.vo.Resource

interface Repo {
    suspend fun getDrinkList(drinkName:String):Resource<List<Drink>>

    suspend fun getDrinkFavorites(): Resource<List<DrinkEntity>>

    suspend fun insertDrink(drink:DrinkEntity)

    suspend fun deleteDrink(drink: DrinkEntity)
}