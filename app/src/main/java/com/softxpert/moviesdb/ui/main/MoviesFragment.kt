package com.softxpert.moviesdb.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.softxpert.moviesdb.databinding.FragmentMoviesBinding
import com.softxpert.moviesdb.domain.model.GenresModel
import com.softxpert.moviesdb.domain.model.category.Category
import com.softxpert.moviesdb.domain.model.category.PopularCategory
import com.softxpert.moviesdb.ui.model.FilterOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviesFragment : Fragment(), GenresAdapter.ONTypeClicked {

    private val viewModel: MoviesViewModel by viewModels()
    private val binding get() = _binding!!
    lateinit var popularAdapter: MoviesAdapter

    private var _binding: FragmentMoviesBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        popularAdapter = createAdapter(PopularCategory())
        popularAdapter.refresh()
        setupPopularRecyclerView(popularAdapter)
        subscribeToPopularViewStateUpdates(popularAdapter)
        subscribeToGenresStateUpdates()
        setupSearch()
    }

    private fun setupSearch() {
        binding.searchTI.setEndIconOnClickListener {
            searchMovies(binding.search.text.toString())
        }
        binding.search.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                searchMovies(binding.search.text.toString())
            }
            false
        }
    }


    private fun setupPopularRecyclerView(moviesAdapter: MoviesAdapter) {
        binding.popularRecycler.apply {
            adapter = moviesAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
        }
    }


    private fun createAdapter(category: Category) = MoviesAdapter(
        MoviesAdapter.MovieClickListener {
            findNavController().navigate(
                MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                    it.id
                )
            )
        }
    ).also {

        it.addLoadStateListener { loadState ->
            category.setupProgress(binding, it.itemCount < 1)

        }
    }

    private fun subscribeToPopularViewStateUpdates(adapter: MoviesAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popular.collect {
                    it?.let { it1 -> adapter.submitData(it1) }
                }
            }
        }
    }

    private fun subscribeToGenresStateUpdates() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.genres.collect {
                    binding.progressBar1.isVisible = false
                    val myNewList: ArrayList<GenresModel> = ArrayList()
                    val allGenre = GenresModel(0, "All", true)
                    if (!myNewList.contains(allGenre))
                        myNewList.add(allGenre)
                    it?.let { it1 -> myNewList.addAll(it1) }
                    binding.genresRv.adapter = GenresAdapter(myNewList, this@MoviesFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTypeClicked(genresModel: GenresModel) {
        var genre: String? = genresModel.id.toString()
        genre = when (genre) {
            "0" -> null
            else -> "$genre"
        }
        viewModel.subscribeToMoviesUpdates(PopularCategory(), genre, FilterOptions.GENRE)
        popularAdapter.refresh()
    }

    private fun searchMovies(key: String) {
        if (key.isNotEmpty()) {
            viewModel.subscribeToMoviesUpdates(PopularCategory(), key, FilterOptions.SEARCH)
            popularAdapter.refresh()
        }

    }
}
