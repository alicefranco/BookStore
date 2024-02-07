package pt.pprojects.bookstorelist.domain.model

import pt.pprojects.domain.ModelInterface

data class PokemonImages(
    val pokemonId: Int,
    val frontDefault: String,
    val backDefault: String?,
    val frontFemale: String?,
    val backFemale: String?,
    val frontShiny: String?,
    val backShiny: String?,
    val frontFemaleShiny: String?,
    val backFemaleShiny: String?
) : ModelInterface