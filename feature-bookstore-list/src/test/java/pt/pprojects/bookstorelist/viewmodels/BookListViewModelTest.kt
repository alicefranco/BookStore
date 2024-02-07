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
import pt.pprojects.bookstorelist.domain.model.Pokemon
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface
import pt.pprojects.bookstorelist.domain.usecase.BooksUseCase
import pt.pprojects.bookstorelist.presentation.mapper.BookDomainPresentationMapper
import pt.pprojects.bookstorelist.presentation.model.ListItem
import pt.pprojects.bookstorelist.presentation.model.PokemonItem
import pt.pprojects.bookstorelist.presentation.bookstorelist.BookListViewModel

class BookListViewModelTest {
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val pokemonRepository: BookRepositoryInterface =
        mock(BookRepositoryInterface::class.java)

    private lateinit var pokeListUsecases: BooksUseCase
    private lateinit var pokeListViewModel: BookListViewModel
    private val pokemonDomainPresentationMapper = BookDomainPresentationMapper()

    @Before
    fun `before each test`() {
        pokeListUsecases = BooksUseCase(pokemonRepository)

        pokeListViewModel = BookListViewModel(
            Schedulers.trampoline(),
            pokeListUsecases,
            pokemonDomainPresentationMapper
        )
    }

    @Test
    fun `get pokemons should return pokemons`() {
        `when`(pokemonRepository.getPokemons(false, 0)).thenReturn(Single.just(pokemonsDomain))
        pokeListViewModel.getPokemons(false)

        assertThat(pokeListViewModel.pokemons.value).isEqualTo(
            Result.Success(
                expectedPokemonPresentation
            )
        )
    }

    @Test
    fun `get pokemons should return error`() {
        `when`(
            pokemonRepository.getPokemons(
                false,
                0
            )
        ).thenReturn(Single.error(NetworkingError.ConnectionTimeout))
        pokeListViewModel.getPokemons(false)

        assertThat(pokeListViewModel.pokemons.value).isEqualToComparingFieldByField(
            Result.Error(
                NetworkingError.ConnectionTimeout
            )
        )
    }

    private val pokemonsDomain = listOf(
        Pokemon(
            pokemonName = "Charmander",
            pokemonId = 4
        ),
        Pokemon(
            pokemonName = "Charmeleon",
            pokemonId = 5
        )
    )

    private val expectedPokemonPresentation = listOf(
        PokemonItem(
            itemType = ListItem.LIST_ITEM,
            name = "Charmander",
            number = "4",
            image = "ic_4"
        ),
        PokemonItem(
            itemType = ListItem.LIST_ITEM,
            name = "Charmeleon",
            number = "5",
            image = "ic_5"
        )
    )
}
