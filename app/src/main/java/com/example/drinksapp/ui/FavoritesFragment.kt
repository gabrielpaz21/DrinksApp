package com.example.drinksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinksapp.AppDataBase
import com.example.drinksapp.DataSource
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.data.model.DrinkEntity
import com.example.drinksapp.databinding.FragmentFavoritesBinding
import com.example.drinksapp.ui.domain.RepoImpl
import com.example.drinksapp.ui.viewModel.MainViewModel
import com.example.drinksapp.ui.viewModel.VMFactory
import com.example.drinksapp.vo.Resource

class FavoritesFragment : Fragment(), MainAdapter.OnDrinkClickListener {

    private var _binding: FragmentFavoritesBinding? = null

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.getDrinkFavorites().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val list = result.data.map {
                        Drink(
                            it.Id,
                            it.image,
                            it.name,
                            it.description,
                            it.hasAlcohol
                        )
                    }
                    binding.rvFavoritesDrink.adapter = MainAdapter(requireContext(), list, this)
                }
                is Resource.Failure -> {

                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvFavoritesDrink.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavoritesDrink.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onDrinkClick(drink: Drink, position: Int) {
        val drinkEntity =
            DrinkEntity(drink.id, drink.image, drink.name, drink.description, drink.hasAlcohol)
        viewModel.deleteDrink(drinkEntity)
        binding.rvFavoritesDrink.adapter?.notifyItemRemoved(position)
    }

}