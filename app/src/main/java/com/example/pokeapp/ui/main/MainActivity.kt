package com.example.pokeapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.databinding.ActivityMainBinding
import com.example.pokeapp.ui.details.DetailsActivity
import com.example.pokeapp.ui.main.epoxy.PokemonListener
import com.example.pokeapp.ui.main.epoxy.PokemonsFeedController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View, PokemonListener {

    @Inject
    lateinit var presenter: MainContract.Presenter
    @Inject
    lateinit var feedController: PokemonsFeedController
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.takeView(this)

        initializeViews()
        loadData()
    }

    private fun initializeViews() {
        binding.recyclerViewPokemons.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = feedController.adapter
        }

        binding.swipeToRefresh.setOnRefreshListener {
            presenter.refreshData()
        }
    }

    private fun loadData() {
        presenter.startLoadingPokemons()
    }

    override fun onDestroy() {
        presenter.dropView()
        super.onDestroy()
    }

    override fun showPokemonsList(pokemons: PagedList<Pokemon>) {
        feedController.submitList(pokemons)
    }

    override fun showLoadingBar(show: Boolean) {
        binding.progressBar.visibility = if(show) View.VISIBLE else View.GONE
    }

    override fun stopLoadingSwipeToRefresh() {
        binding.swipeToRefresh.isRefreshing = false
    }

    override fun showErrorOnPokemonsList() {
        Toast.makeText(this, "Error loading stuff!", Toast.LENGTH_LONG).show()
    }

    override fun showEndOfList() {
        feedController.endReached = true
    }

    override fun onClickListener(name: String) {
        startActivity(Intent(this, DetailsActivity::class.java))
    }
}