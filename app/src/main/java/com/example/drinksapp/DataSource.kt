package com.example.drinksapp

import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.data.model.DrinkEntity
import com.example.drinksapp.vo.Resource
import com.example.drinksapp.vo.RetrofitClient

class DataSource(private val appDataBase: AppDataBase) {

    suspend fun getDrinkByName(drinkName: String): Resource<List<Drink>> =
        Resource.Success(RetrofitClient.webService.getDrinkByName(drinkName).drinkList)

    suspend fun insertDrinkIntoRoom(drink: DrinkEntity) {
        appDataBase.drinkDao().insertFavorite(drink)
    }

    suspend fun getDrinkFavorites(): Resource<List<DrinkEntity>> =
        Resource.Success(appDataBase.drinkDao().getAllDrinks())

    suspend fun deleteDrink(drink: DrinkEntity) {
        appDataBase.drinkDao().deleteDrink(drink)
    }

}