package com.example.drinksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinksapp.AppDataBase
import com.example.drinksapp.DataSource
import com.example.drinksapp.R
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.databinding.FragmentMainBinding
import com.example.drinksapp.ui.domain.RepoImpl
import com.example.drinksapp.ui.viewModel.MainViewModel
import com.example.drinksapp.ui.viewModel.VMFactory
import com.example.drinksapp.vo.Resource

class MainFragment : Fragment(), MainAdapter.OnDrinkClickListener {

    private var _binding: FragmentMainBinding? = null

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setupSearchView()
        setUpObservers()
        binding.btnGoToFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_favoritesFragment)
        }
    }

    private fun setUpObservers() {
        viewModel.fetchDrinkList.observe(viewLifecycleOwner) { result ->

            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvDrinks.adapter = MainAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "it has been a problem to fetch data ${result.exception}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        with(binding.rvDrinks) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun setupSearchView() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setDrink(query!!.trim().lowercase())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })

    }

    override fun onDrinkClick(drink: Drink,position:Int) {
        val bundle = Bundle()
        bundle.putParcelable("drink", drink)

        findNavController().navigate(
            R.id.action_mainFragment_to_drinksDetailFragment,
            bundle
        )

    }
}