package pt.pprojects.bookstorelist.presentation.bookstorelist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pt.pprojects.domain.Result
import pt.pprojects.bookstorelist.R
import pt.pprojects.bookstorelist.databinding.ActivityPokelistBinding
import pt.pprojects.bookstorelist.presentation.gone
import pt.pprojects.bookstorelist.presentation.model.PokemonItem
import pt.pprojects.bookstorelist.presentation.bookdetails.BookDetailsActivity
import pt.pprojects.bookstorelist.presentation.showDialog
import pt.pprojects.bookstorelist.presentation.visible

class BookListActivity : AppCompatActivity() {

    private val pokeListViewModel: BookListViewModel by viewModel()
    private lateinit var pokemonsAdapter: PokemonsAdapter
    private val layoutManager = LinearLayoutManager(this)

    private lateinit var binding: ActivityPokelistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokelistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()

        pokeListViewModel.pokemons.observe(this) {
            handleResult(it)
        }

        getPokemons()
    }

    private fun setupRecycler() {
        pokemonsAdapter = PokemonsAdapter(this)
        pokemonsAdapter.addPokemonItemClick(pokemonItemClick)
        pokemonsAdapter.addLoadMoreAction(loadMoreAction)

        binding.rvPokemons.layoutManager = layoutManager
        binding.rvPokemons.adapter = pokemonsAdapter
    }

    private fun handleResult(result: Result<List<PokemonItem>>) {
        when (result) {
            is Result.Success -> {
                binding.pbListLoading.gone()
                binding.rvPokemons.visible()
                pokemonsAdapter.addPokemons(result.data, pokeListViewModel.loadedAll)
                pokemonsAdapter.notifyDataSetChanged()
            }
            is Result.Loading -> {
            }
            is Result.Error -> {
                showErrorDialog(getString(R.string.error_title), result.cause.message)
            }
        }
    }

    private fun showErrorDialog(
        title: String,
        message: String?
    ) {
        this.showDialog(
            title,
            message + getString(R.string.error_reload_message),
            positiveAction = {
                getPokemons()
            },
            negativeAction = {
                finish()
            }
        )
    }

    private fun getPokemons() {
        pokeListViewModel.getPokemons()
    }

    private val pokemonItemClick: (pokemonId: Int) -> Unit = { pokemonId ->
        openPokemonDetailsScreen(pokemonId)
    }

    private val loadMoreAction: () -> Unit = {
        pokeListViewModel.getPokemons(false)
    }

    private fun openPokemonDetailsScreen(pokemonId: Int) {
        val intent = Intent(this, BookDetailsActivity::class.java)
        intent.putExtra(POKEMON_ID, pokemonId)
        startActivity(intent)
    }

    companion object {
        const val POKEMON_ID = "POKEMON_ID"
    }
}