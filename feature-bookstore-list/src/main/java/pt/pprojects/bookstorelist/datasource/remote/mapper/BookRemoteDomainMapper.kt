package pt.pprojects.bookstorelist.datasource.remote.mapper

import pt.pprojects.bookstorelist.datasource.remote.model.*
import pt.pprojects.bookstorelist.domain.model.*
import pt.pprojects.bookstorelist.domain.model.PokemonAbility
import pt.pprojects.bookstorelist.domain.model.PokemonMove
import pt.pprojects.bookstorelist.domain.model.PokemonType

class BookRemoteDomainMapper {
    companion object {
        const val POKEMON_URL = "https://pokeapi.co/api/v2/pokemon/"
        const val TYPE_URL = "https://pokeapi.co/api/v2/type/"
        const val ABILITY_URL = "https://pokeapi.co/api/v2/ability/"
        const val MOVE_URL = "https://pokeapi.co/api/v2/move/"
    }

    fun mapBooksToDomain(
        bookResponse: List<BookResponse>
    ): List<Book> {
        return bookResponse.map {
            Book(
                authors = it.volumeInfo.authors,
                image = it.volumeInfo.imageLinks?.thumbnail ?: "",
                title = it.volumeInfo.title
            )
        }
    }

    fun mapPokemonCharacteristicsToDomain(
        pokemonCharacteristicsResponse: PokemonCharacteristicsResponse
    ): PokemonCharacteristics {
        val pokemon = pokemonCharacteristicsResponse
        return PokemonCharacteristics(
            pokemonId = pokemon.id,
            pokemonName = pokemon.name,
            baseExperience = pokemon.base_experience,
            types = getPokemonTypes(pokemon.types),
            images = getPokemonImages(
                pokemonId = pokemon.id,
                sprites = pokemon.sprites
            ),
            height = pokemon.height,
            weight = pokemon.weight,
            abilities = getPokemonAbilities(pokemon.abilities),
            moves = getPokemonMoves(pokemon.moves)
        )
    }

    private fun getPokemonImages(
        pokemonId: Int,
        sprites: PokemonSpriteResponse
    ) = PokemonImages(
        pokemonId = pokemonId,
        frontDefault = sprites.front_default,
        backDefault = sprites.back_default,
        frontFemale = sprites.front_female,
        backFemale = sprites.back_female,
        frontShiny = sprites.front_shiny,
        backShiny = sprites.back_shiny,
        frontFemaleShiny = sprites.front_shiny_female,
        backFemaleShiny = sprites.back_shiny_female
    )

    private fun getPokemonTypes(
        types: List<PokemonTypeResponse>
    ): List<PokemonType> {
        val typesList = arrayListOf<PokemonType>()
        types.forEach { it ->
            typesList.add(
                PokemonType(
                    typeId = getId(it.type.url,
                        TYPE_URL
                    ),
                    typeName = it.type.name
                )
            )
        }
        return typesList
    }

    private fun getPokemonAbilities(
        abilities: List<PokemonAbilityResponse>
    ): List<PokemonAbility> {
        val abilitiesList = arrayListOf<PokemonAbility>()
        abilities.forEach { it ->
            abilitiesList.add(
                PokemonAbility(
                    abiltiyId = getId(it.ability.url,
                        ABILITY_URL
                    ),
                    abilityName = it.ability.name,
                    isHidden = it.is_hidden
                )
            )
        }
        return abilitiesList
    }
    private fun getPokemonMoves(
        abilities: List<PokemonMoveResponse>
    ): List<PokemonMove> {
        val movesList = arrayListOf<PokemonMove>()
        abilities.forEach { it ->
            movesList.add(
                PokemonMove(
                    moveId = getId(it.move.url,
                        MOVE_URL
                    ),
                    moveName = it.move.name
                )
            )
        }
        return movesList
    }

    private fun getId(
        url: String,
        urlBase: String
    ): Int {
        val numberOfCharsToDrop = urlBase.length
        return if (url.last() == '/') {
            url.drop(numberOfCharsToDrop)
                .dropLast(1)
                .toInt()
        } else {
            url.drop(numberOfCharsToDrop)
                .toInt()
        }
    }
}