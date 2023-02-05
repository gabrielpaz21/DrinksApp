package com.example.drinksapp.ui.domain

import com.example.drinksapp.DataSource
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.data.model.DrinkEntity
import com.example.drinksapp.data.model.Repo
import com.example.drinksapp.vo.Resource

class RepoImpl(private val dataSource: DataSource) : Repo {

    override suspend fun getDrinkList(drinkName: String): Resource<List<Drink>> =
        dataSource.getDrinkByName(drinkName)

    override suspend fun getDrinkFavorites(): Resource<List<DrinkEntity>> = dataSource.getDrinkFavorites()

    override suspend fun insertDrink(drink: DrinkEntity) {
        dataSource.insertDrinkIntoRoom(drink)
    }

    override suspend fun deleteDrink(drink: DrinkEntity) {
        dataSource.deleteDrink(drink)
    }

}