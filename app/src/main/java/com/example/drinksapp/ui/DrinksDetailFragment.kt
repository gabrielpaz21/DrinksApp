package com.example.drinksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.drinksapp.AppDataBase
import com.example.drinksapp.DataSource
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.data.model.DrinkEntity
import com.example.drinksapp.databinding.FragmentDrinksDetailBinding
import com.example.drinksapp.ui.domain.RepoImpl
import com.example.drinksapp.ui.viewModel.MainViewModel
import com.example.drinksapp.ui.viewModel.VMFactory

class DrinksDetailFragment : Fragment() {

    private lateinit var drink: Drink

    private var _binding: FragmentDrinksDetailBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel> {
        VMFactory(
            RepoImpl(
                DataSource(
                    AppDataBase.getDatabase(requireContext().applicationContext)
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            drink = it.getParcelable("drink")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDrinksDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(drink.image).centerCrop().into(binding.ivDrink)
        binding.tvTitle.text = drink.name
        binding.tvDescription.text = drink.description

        if (drink.hasAlcohol == "Non_Alcoholic") {
            binding.tvHasAlcohol.text = "Drink without alcoholic"
        } else {
            binding.tvHasAlcohol.text = "Drink with alcoholic"
        }

        binding.fabSaveDrink.setOnClickListener {
            viewModel.insertDrink(
                DrinkEntity(
                    drink.id,
                    drink.image,
                    drink.name,
                    drink.description,
                    drink.hasAlcohol
                )
            )
            Toast.makeText(requireContext(),"Se guardo el trago a favoritos",Toast.LENGTH_LONG).show()
        }

    }
}