package pt.pprojects.bookstorelist.presentation.mapper

import pt.pprojects.bookstorelist.R
import pt.pprojects.bookstorelist.domain.model.*
import pt.pprojects.bookstorelist.presentation.model.*

class BookDomainPresentationMapper {

    companion object {
        const val METER = "m"
        const val KILOGRAM = "Kg"
        const val HIDDEN = " (hidden)"
        const val NUMBER_MARKER = "#"
        const val IMAGE_PREFFIX = "ic_"
        const val IMAGE_POKEMON_NOT_FOUND = "ic_0"

        const val FIRE_TYPE = "fire"
        const val WATER_TYPE = "water"
        const val GRASS_TYPE = "grass"
        const val ELECTRIC_TYPE = "electric"
        const val ICE_TYPE = "ice"
        const val FAIRY_TYPE = "fairy"
        const val NORMAL_TYPE = "normal"
        const val PSYCHIC_TYPE = "psychic"
        const val DARK_TYPE = "dark"
        const val GHOST_TYPE = "ghost"
        const val POISON_TYPE = "poison"
        const val BUG_TYPE = "bug"
        const val FLYING_TYPE = "flying"
        const val DRAGON_TYPE = "dragon"
        const val FIGHTING_TYPE = "fighting"
        const val GROUND_TYPE = "ground"
        const val ROCK_TYPE = "rock"
        const val STEEL_TYPE = "steel"
    }

    fun mapBooksToPresentation(
        books: List<Book>
    ): List<BookItem> {
        return books.map {
            BookItem(
                itemType = ListItem.LIST_ITEM,
                image = it.image,
                authors = it.authors,
                title = it.title
            )
        }
    }

    private fun getDefaultPokemonImage(pokemonId: Int): String {
        if (pokemonId <= 807)
            return IMAGE_PREFFIX + pokemonId.toString()
        else
            return IMAGE_POKEMON_NOT_FOUND
    }

    fun mapPokemonDetailsToPresentation(
        pokemonDetails: PokemonCharacteristics
    ): PokemonDetails {
        return PokemonDetails(
            pokemonName = pokemonDetails.pokemonName.capitalize(),
            pokemonNumber = NUMBER_MARKER + pokemonDetails.pokemonId.toString(),
            baseExperience = pokemonDetails.baseExperience.toString(),
            height = formatHeight(pokemonDetails.height),
            weight = formatWeight(pokemonDetails.weight),
            images = getImageResources(pokemonDetails.images),
            types = getTypes(pokemonDetails.types),
            moves = getMoves(pokemonDetails.moves),
            abilities = getAbilities(pokemonDetails.abilities)
        )
    }

    private fun formatHeight(height: Int): String {
        val heightInMeters = height.toDouble() / 10
        return heightInMeters.toString() + METER
    }

    private fun formatWeight(weight: Int): String {
        val weightInKilograms = weight.toDouble() / 10
        return weightInKilograms.toString() + KILOGRAM
    }

    private fun getTypes(types: List<PokemonType>): List<TypeItem> {
        val typesList: ArrayList<TypeItem> = arrayListOf()
        types.forEach {
            typesList.add(
                TypeItem(
                    it.typeName.toUpperCase(),
                    image = getTypeImage(it.typeName)
                )
            )
        }
        return typesList
    }

    private fun getAbilities(abilities: List<PokemonAbility>): List<DetailItem> {
        val abilitiesList: ArrayList<DetailItem> = arrayListOf()
        abilities.forEach {
            abilitiesList.add(
                DetailItem(
                    description = getAbilityDescription(
                        it.abilityName.capitalize(),
                        it.isHidden
                    )
                )
            )
        }
        return abilitiesList
    }

    private fun getAbilityDescription(name: String, isHidden: Boolean): String {
        var description: String = name
        if (isHidden) {
            description += HIDDEN
        }
        return description
    }

    private fun getMoves(moves: List<PokemonMove>): List<DetailItem> {
        val movesList: ArrayList<DetailItem> = arrayListOf()
        moves.forEach {
            movesList.add(
                DetailItem(description = it.moveName.capitalize())
            )
        }
        return movesList
    }

    private fun getImageResources(
        images: PokemonImages
    ): PokemonImagesResources {
        return PokemonImagesResources(
            frontDefault = images.frontDefault,
            frontShiny = images.frontShiny,
            frontFemale = images.frontFemale,
            frontFemaleShiny = images.frontFemaleShiny
        )
    }

    private fun getTypeImage(type: String): Int {
        var imageResource = R.drawable.ic_fire
        when (type) {
            FIRE_TYPE -> {
                imageResource = R.drawable.ic_fire
            }
            WATER_TYPE -> {
                imageResource = R.drawable.ic_water
            }
            GRASS_TYPE -> {
                imageResource = R.drawable.ic_grass
            }
            ELECTRIC_TYPE -> {
                imageResource = R.drawable.ic_eletric
            }
            ICE_TYPE -> {
                imageResource = R.drawable.ic_ice
            }
            FAIRY_TYPE -> {
                imageResource = R.drawable.ic_fairy
            }
            NORMAL_TYPE -> {
                imageResource = R.drawable.ic_normal
            }
            PSYCHIC_TYPE -> {
                imageResource = R.drawable.ic_psychic
            }
            DARK_TYPE -> {
                imageResource = R.drawable.ic_dark
            }
            GHOST_TYPE -> {
                imageResource = R.drawable.ic_ghost
            }
            POISON_TYPE -> {
                imageResource = R.drawable.ic_poison
            }
            BUG_TYPE -> {
                imageResource = R.drawable.ic_bug
            }
            FLYING_TYPE -> {
                imageResource = R.drawable.ic_flying
            }
            DRAGON_TYPE -> {
                imageResource = R.drawable.ic_dragon
            }
            FIGHTING_TYPE -> {
                imageResource = R.drawable.ic_fighting
            }
            GROUND_TYPE -> {
                imageResource = R.drawable.ic_ground
            }
            ROCK_TYPE -> {
                imageResource = R.drawable.ic_rock
            }
            STEEL_TYPE -> {
                imageResource = R.drawable.ic_steel
            }
        }
        return imageResource
    }
}