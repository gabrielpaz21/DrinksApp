package com.example.drinksapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drink(

    @SerializedName("idDrink")
    val id: String = "",

    @SerializedName("strDrinkThumb")
    val image: String = "",

    @SerializedName("strDrink")
    val name: String,

    @SerializedName("strInstructions")
    val description: String = "",

    @SerializedName("strAlcoholic")
    val hasAlcohol: String = "Non_Alcoholic"

) : Parcelable

data class DrinkList(
    @SerializedName("drinks")
    val drinkList: List<Drink>
)

@Entity(tableName = "drinkEntity")
data class DrinkEntity(

    @PrimaryKey
    val Id: String,

    @ColumnInfo("image")
    val image: String,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("has_alcohol")
    val hasAlcohol: String
)
