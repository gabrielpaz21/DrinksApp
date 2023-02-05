package com.example.drinksapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinksapp.R
import com.example.drinksapp.base.BaseViewHolder
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.databinding.DrinkRowBinding

class MainAdapter(
    private val context: Context,
    private val drinksList: List<Drink>,
    private val itemClickListener: OnDrinkClickListener
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnDrinkClickListener {
        fun onDrinkClick(drink: Drink,position:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> =
        MainViewHolder(LayoutInflater.from(context).inflate(R.layout.drink_row, parent, false))

    override fun getItemCount(): Int = drinksList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(drinksList[position], position)
        }
    }

    inner class MainViewHolder(itemView: View) : BaseViewHolder<Drink>(itemView) {

        private val binding = DrinkRowBinding.bind(itemView)

        override fun bind(item: Drink, position: Int) {
            Glide.with(context).load(item.image).centerCrop().into(binding.ivDrink)
            with(binding) {
                tvTitle.text = item.name
                tvDescription.text = item.description
                itemView.setOnClickListener { itemClickListener.onDrinkClick(item,position) }
            }
        }
    }

}


