package pt.pprojects.bookstorelist.repository

import io.reactivex.rxjava3.core.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import pt.pprojects.network.error.NetworkingError
import pt.pprojects.bookstorelist.domain.model.*

class BookRepositoryTest {
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    private val remoteDataSource: pt.pprojects.bookstorelist.data.datasource.BookRemoteDataSourceInterface =
        mock(pt.pprojects.bookstorelist.data.datasource.BookRemoteDataSourceInterface::class.java)
    private lateinit var pokemonRepository: pt.pprojects.bookstorelist.data.repository.BookRepository

    @Before
    fun `before each test`() {
        pokemonRepository =
            pt.pprojects.bookstorelist.data.repository.BookRepository(remoteDataSource)
    }

    @Test
    fun `repository get pokemons should return pokemons`() {
        `when`(
            remoteDataSource
                .getBooks(0)
        ).thenReturn(Single.just(pokemonsDomain))

        val result = pokemonRepository.getBooks(false, 0)

        assertThat(result).isEqualToComparingFieldByField(Single.just(expectedPokemonsDomain))
    }

    @Test
    fun `repository get pokemons should return error`() {
        `when`(
            remoteDataSource
                .getBooks(0)
        ).thenReturn(Single.error(NetworkingError.ConnectionTimeout))

        val testObserver = pokemonRepository.getBooks(false, 0).test()

        testObserver
            .assertNoValues()
            .assertNotComplete()
            .assertError(NetworkingError.ConnectionTimeout)
    }

    @Test
    fun `repository get pokemon characteristics should return pokemon characteristics`() {
        `when`(
            remoteDataSource
                .getBookDetails(pokemonCharsDomain.pokemonId)
        ).thenReturn(Single.just(pokemonCharsDomain))

        val result = pokemonRepository.getPokemonCharacteristics(
            false,
            expectedPokemonCharsDomain.pokemonId
        )

        assertThat(result).isEqualToComparingFieldByField(Single.just(expectedPokemonCharsDomain))
    }

    @Test
    fun `repository get pokemon characteristics should return error`() {
        `when`(
            remoteDataSource
                .getBookDetails(pokemonCharsDomain.pokemonId)
        ).thenReturn(Single.error(NetworkingError.ConnectionTimeout))

        val testObserver = pokemonRepository.getPokemonCharacteristics(
            false,
            expectedPokemonCharsDomain.pokemonId
        ).test()

        testObserver
            .assertNoValues()
            .assertNotComplete()
            .assertError(NetworkingError.ConnectionTimeout)
    }

    private val pokemonsDomain = listOf(
        Pokemon(
            pokemonName = "bulbasaur",
            pokemonId = 1
        ),
        Pokemon(
            pokemonName = "ivysaur",
            pokemonId = 2
        )
    )
    private val expectedPokemonsDomain = listOf(
        Pokemon(
            pokemonName = "bulbasaur",
            pokemonId = 1
        ),
        Pokemon(
            pokemonName = "ivysaur",
            pokemonId = 2
        )
    )

    private val pokemonCharsDomain = PokemonCharacteristics(
        pokemonId = 4,
        pokemonName = "Charmander",
        baseExperience = 50,
        types = listOf(),
        height = 5,
        weight = 15,
        moves = listOf(
            PokemonMove(
                moveName = "move",
                moveId = 5
            )
        ),
        abilities = listOf(
            PokemonAbility(
                abilityName = "ability",
                abiltiyId = 10,
                isHidden = true
            )
        ),
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

    private val expectedPokemonCharsDomain = PokemonCharacteristics(
        pokemonId = 4,
        pokemonName = "Charmander",
        baseExperience = 50,
        types = listOf(),
        height = 5,
        weight = 15,
        moves = listOf(
            PokemonMove(
                moveName = "move",
                moveId = 5
            )
        ),
        abilities = listOf(
            PokemonAbility(
                abilityName = "ability",
                abiltiyId = 10,
                isHidden = true
            )
        ),
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
}