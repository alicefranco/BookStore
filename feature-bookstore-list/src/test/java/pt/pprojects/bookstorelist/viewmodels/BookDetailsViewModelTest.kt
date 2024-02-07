package pt.pprojects.bookstorelist.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import pt.pprojects.domain.Result
import pt.pprojects.network.error.NetworkingError
import pt.pprojects.bookstorelist.R
import pt.pprojects.bookstorelist.domain.model.PokemonCharacteristics
import pt.pprojects.bookstorelist.domain.model.PokemonImages
import pt.pprojects.bookstorelist.domain.model.PokemonType
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface
import pt.pprojects.bookstorelist.domain.usecase.BookDetailsUseCase
import pt.pprojects.bookstorelist.presentation.mapper.BookDomainPresentationMapper
import pt.pprojects.bookstorelist.presentation.model.PokemonDetails
import pt.pprojects.bookstorelist.presentation.model.PokemonImagesResources
import pt.pprojects.bookstorelist.presentation.model.TypeItem
import pt.pprojects.bookstorelist.presentation.bookdetails.BookDetailsViewModel

class BookDetailsViewModelTest {
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val pokemonRepository: BookRepositoryInterface =
        mock(BookRepositoryInterface::class.java)

    private lateinit var pokemonCharsUsecases: BookDetailsUseCase
    private lateinit var pokemonDetailsViewModel: BookDetailsViewModel
    private val pokemonDomainPresentationMapper = BookDomainPresentationMapper()

    @Before
    fun `before each test`() {
        pokemonCharsUsecases = BookDetailsUseCase(pokemonRepository)

        pokemonDetailsViewModel = BookDetailsViewModel(
            Schedulers.trampoline(),
            pokemonCharsUsecases,
            pokemonDomainPresentationMapper
        )
    }

    @Test
    fun `get pokemon characteristics should return pokemon characteristics`() {
        `when`(
            pokemonRepository
                .getPokemonCharacteristics(false, pokemonCharsDomain.pokemonId)
        ).thenReturn(Single.just(pokemonCharsDomain))

        pokemonDetailsViewModel.getPokemonDetails(pokemonCharsDomain.pokemonId)

        assertThat(pokemonDetailsViewModel.pokemonDetails.value)
            .isEqualTo(
                Result.Success(
                    expectedPokemonCharsPresentation
                )
            )
    }

    @Test
    fun `get pokemon characteristics should return error`() {
        `when`(
            pokemonRepository
                .getPokemonCharacteristics(false, pokemonCharsDomain.pokemonId)
        ).thenReturn(Single.error(NetworkingError.ConnectionTimeout))

        pokemonDetailsViewModel.getPokemonDetails(pokemonCharsDomain.pokemonId)

        assertThat(pokemonDetailsViewModel.pokemonDetails.value)
            .isEqualToComparingFieldByField(
                Result.Error(
                    NetworkingError.ConnectionTimeout
                )
            )
    }

    private val pokemonCharsDomain = PokemonCharacteristics(
        pokemonId = 4,
        pokemonName = "charmander",
        baseExperience = 50,
        types = listOf(
            PokemonType(
                typeId = 1,
                typeName = "fire"
            )
        ),
        height = 5,
        weight = 15,
        moves = listOf(),
        abilities = listOf(),
        images = PokemonImages(
            pokemonId = 4,
            frontDefault = "",
            backDefault = null,
            frontFemale = null,
            backFemale = null,
            frontShiny = null,
            backShiny = null,
            frontFemaleShiny = null,
            backFemaleShiny = null
        )
    )

    private val expectedPokemonCharsPresentation = PokemonDetails(
        pokemonNumber = "#4",
        pokemonName = "Charmander",
        baseExperience = "50",
        types = listOf(
            TypeItem(
                name = "FIRE",
                image = R.drawable.ic_fire
            )
        ),
        height = "0.5m",
        weight = "1.5Kg",
        moves = listOf(),
        abilities = listOf(),
        images = PokemonImagesResources(
            frontDefault = "",
            frontFemale = null,
            frontShiny = null,
            frontFemaleShiny = null
        )
    )
}