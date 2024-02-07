package pt.pprojects.bookstorelist.domain.model

import pt.pprojects.domain.ModelInterface

data class Pokemon(
    val pokemonId: Int,
    val pokemonName: String
) : ModelInterface
