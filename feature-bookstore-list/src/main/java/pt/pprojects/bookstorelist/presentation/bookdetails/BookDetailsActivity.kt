package pt.pprojects.bookstorelist.presentation.bookdetails

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import pt.pprojects.domain.Result
import pt.pprojects.bookstorelist.R
import pt.pprojects.bookstorelist.databinding.ActivityBookDetailsBinding
import pt.pprojects.bookstorelist.presentation.model.PokemonDetails
import pt.pprojects.bookstorelist.presentation.bookstorelist.BookListActivity
import pt.pprojects.bookstorelist.presentation.gone
import pt.pprojects.bookstorelist.presentation.model.DetailItem
import pt.pprojects.bookstorelist.presentation.model.PokemonImagesResources
import pt.pprojects.bookstorelist.presentation.model.TypeItem
import pt.pprojects.bookstorelist.presentation.setOptionalImage
import pt.pprojects.bookstorelist.presentation.showDialog
import pt.pprojects.bookstorelist.presentation.visible

class BookDetailsActivity : AppCompatActivity() {
    private val pokemonDetailsViewModel: BookDetailsViewModel by viewModel()
    private var pokemonId: Int? = null

    private lateinit var binding: ActivityBookDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokemonId = intent.getIntExtra(BookListActivity.POKEMON_ID, 1)

        binding.ivClose.setOnClickListener {
            closePokemonDetailsScreen()
        }

        pokemonId?.let { pokemonDetailsViewModel.getPokemonDetails(it) }

        pokemonDetailsViewModel.pokemonDetails.observe(this) {
            handleResult(it)
        }
    }

    private fun handleResult(result: Result<PokemonDetails>) {
        when (result) {
            is Result.Success -> {
                setPokemonDetails(result.data)
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
            message,
            positiveAction = {
                pokemonId?.let { pokemonDetailsViewModel.getPokemonDetails(it) }
            },
            negativeAction = {
                finish()
            }
        )
    }

    private fun setPokemonDetails(details: PokemonDetails) {
        setImageWithGlide(binding.ivPokemon, details.images.frontDefault)

        binding.ivFemale.setOptionalImage(details.images.frontFemale, this)
        binding.ivShiny.setOptionalImage(details.images.frontShiny, this)
        binding.ivFemaleShiny.setOptionalImage(details.images.frontFemaleShiny, this)

        setGenders(details.pokemonNumber, details.images)

        binding.tvPokemonNumber.text = details.pokemonNumber
        binding.tvPokemonName.text = details.pokemonName
        binding.tvBasexpValue.text = details.baseExperience
        binding.tvWeightValue.text = details.weight
        binding.tvHeightValue.text = details.height

        setTypes(details.types)
        setMoves(details.moves)
        setAbilities(details.abilities)

        binding.pbPokemonDetails.gone()
        binding.layoutPokemonDetails.visible()
    }

    private fun setGenders(pokemonNumber: String, images: PokemonImagesResources) {
        if (images.frontFemale.isNullOrEmpty()) {
            when (pokemonNumber.drop(1).toInt()) {
                in 29..31 -> {
                    binding.ivGenderDefault.setImageResource(R.drawable.ic_gender_female)
                    binding.ivGenderDefaultShiny.setImageResource(R.drawable.ic_gender_female_shiny)
                }
                in 32..34 -> {
                    binding.ivGenderDefault.setImageResource(R.drawable.ic_gender_male)
                    binding.ivGenderDefaultShiny.setImageResource(R.drawable.ic_gender_male_shiny)
                }
                else -> {
                    binding.ivGenderDefault.gone()
                    binding.ivGenderDefaultShiny.setImageResource(R.drawable.ic_genderless_shiny)
                }
            }
        }
    }

    private fun setTypes(types: List<TypeItem>) {
        when (types.size) {
            1 -> {
                binding.tvType1.text = types[0].name
                binding.ivType1.setImageResource(types[0].image)
            }
            2 -> {
                binding.tvType1.text = types[0].name
                binding.ivType1.setImageResource(types[0].image)

                binding.tvType2.text = types[1].name
                binding.ivType2.setImageResource(types[1].image)
                binding.llType2.visible()
            }
            else -> {
                binding.llType1.gone()
                binding.llTypeUnknown.visible()
            }
        }
    }

    private fun setMoves(moves: List<DetailItem>) {
        val movesLayoutManager = LinearLayoutManager(this)
        val movesAdapter = DetailsAdapter()
        movesAdapter.addDetails(moves)

        binding.rvMoves.layoutManager = movesLayoutManager
        binding.rvMoves.adapter = movesAdapter
    }

    private fun setAbilities(abilities: List<DetailItem>) {
        val abilitiesLayoutManager = LinearLayoutManager(this)
        val abilitiesAdapter = DetailsAdapter()
        abilitiesAdapter.addDetails(abilities)

        binding.rvAbilities.layoutManager = abilitiesLayoutManager
        binding.rvAbilities.adapter = abilitiesAdapter
    }

    private fun setImageWithGlide(imageView: ImageView, resource: String) {
        Glide.with(this)
            .load(resource)
            .placeholder(R.mipmap.ic_launcher_foreground)
            .into(imageView)
    }

    private fun closePokemonDetailsScreen() {
        finish()
    }
}