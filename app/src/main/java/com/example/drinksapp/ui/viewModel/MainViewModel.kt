package com.example.drinksapp.ui.viewModel

import androidx.lifecycle.*
import com.example.drinksapp.data.model.DrinkEntity
import com.example.drinksapp.data.model.Repo
import com.example.drinksapp.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo) : ViewModel() {

    private val drinkData = MutableLiveData<String>()

    init {
        setDrink("margarita")
    }

    fun setDrink(drinkName: String) {
        drinkData.value = drinkName
    }

    val fetchDrinkList = drinkData.distinctUntilChanged().switchMap { nameDrink ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getDrinkList(nameDrink))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun insertDrink(drink: DrinkEntity) {
        viewModelScope.launch {
            repo.insertDrink(drink)
        }
    }

    fun getDrinkFavorites() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getDrinkFavorites())
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }

    }

    fun deleteDrink(drink: DrinkEntity) {
        viewModelScope.launch {
            repo.deleteDrink(drink)
        }
    }

}