package pt.pprojects.bookstorelist.mappers

import org.junit.Test

import pt.pprojects.bookstorelist.domain.model.Pokemon
import org.assertj.core.api.Assertions.assertThat
import pt.pprojects.bookstorelist.R
import pt.pprojects.bookstorelist.domain.model.PokemonCharacteristics
import pt.pprojects.bookstorelist.domain.model.PokemonImages
import pt.pprojects.bookstorelist.domain.model.PokemonType
import pt.pprojects.bookstorelist.presentation.mapper.BookDomainPresentationMapper
import pt.pprojects.bookstorelist.presentation.model.*

class BookDomainPresentationMapperTest {

    private val pokemonDomainPresentationMapper = BookDomainPresentationMapper()

    @Test
    fun `should return domain pokemons list`() {
        val result = pokemonDomainPresentationMapper
            .mapPokemonsToPresentation(pokemonsDomain)

        assertThat(result).isEqualTo(expectedPokemonsPresentation)
    }

    @Test
    fun `should return empty list`() {
        val result = pokemonDomainPresentationMapper
            .mapPokemonsToPresentation(pokemonsDomainEmpty)

        assertThat(result).isEqualTo(emptyList<PokemonItem>())
    }

    @Test
    fun `should return domain pokemon characteristics`() {
        val result = pokemonDomainPresentationMapper
            .mapPokemonDetailsToPresentation(pokemonCharsDomain)

        assertThat(result).isEqualTo(expectedPokemonCharsPresentation)
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

    private val expectedPokemonsPresentation = listOf(
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

    private val pokemonsDomainEmpty = listOf<Pokemon>()

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
