package pt.pprojects.bookstorelist.domain.model

import pt.pprojects.domain.ModelInterface

data class PokemonMove(
    val moveId: Int,
    val moveName: String
) : ModelInterface