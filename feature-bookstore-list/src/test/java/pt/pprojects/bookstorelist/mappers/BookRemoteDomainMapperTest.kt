package pt.pprojects.bookstorelist.mappers

import org.junit.Test

import pt.pprojects.bookstorelist.datasource.remote.mapper.BookRemoteDomainMapper
import pt.pprojects.bookstorelist.domain.model.Pokemon
import org.assertj.core.api.Assertions.assertThat
import pt.pprojects.bookstorelist.datasource.remote.model.*
import pt.pprojects.bookstorelist.domain.model.PokemonCharacteristics
import pt.pprojects.bookstorelist.domain.model.PokemonImages

class BookRemoteDomainMapperTest {

    private val pokemonRemoteDomainMapper = BookRemoteDomainMapper()

    @Test
    fun `should return domain pokemons list`() {
        val result = pokemonRemoteDomainMapper
            .mapPokemonsToDomain(pokemonsResponse)

        assertThat(result).isEqualTo(expectedPokemonsDomain)
    }

    @Test
    fun `should return empty list`() {
        val result = pokemonRemoteDomainMapper
            .mapPokemonsToDomain(pokemonsResponseEmpty)

        assertThat(result).isEqualTo(emptyList<Pokemon>())
    }

    @Test
    fun `should return domain pokemon characteristics`() {
        val result = pokemonRemoteDomainMapper
            .mapPokemonCharacteristicsToDomain(pokemonCharsResponse)

        assertThat(result).isEqualTo(expectedPokemonCharsDomain)
    }

    private val pokemonsResponse = listOf(
        PokemonResponse(
            name = "Charmander",
            url = "https://pokeapi.co/api/v2/pokemon/4/"
        ),
        PokemonResponse(
            name = "",
            url = "https://pokeapi.co/api/v2/pokemon/5"
        )
    )

    private val expectedPokemonsDomain = listOf(
        Pokemon(
            pokemonName = "Charmander",
            pokemonId = 4
        ),
        Pokemon(
            pokemonName = "",
            pokemonId = 5
        )
    )

    private val pokemonsResponseEmpty = listOf<PokemonResponse>()

    private val pokemonCharsResponse = PokemonCharacteristicsResponse(
            id = 4,
            name = "Charmander",
            base_experience = 50,
            types = listOf(),
            height = 5,
            weight = 15,
            moves = listOf(
                PokemonMoveResponse(
                    move = PokemonMove(
                        name = "move",
                        url = "https://pokeapi.co/api/v2/move/5/"
                    )
                )
            ),
            abilities = listOf(
                PokemonAbilityResponse(
                    PokemonAbility(
                        name = "ability",
                        url = "https://pokeapi.co/api/v2/ability/10"
                    ),
                    is_hidden = true
                )
            ),
            sprites = PokemonSpriteResponse(
                front_default = ""
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
            pt.pprojects.bookstorelist.domain.model.PokemonMove(
                moveName = "move",
                moveId = 5
            )
        ),
        abilities = listOf(
            pt.pprojects.bookstorelist.domain.model.PokemonAbility(
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
