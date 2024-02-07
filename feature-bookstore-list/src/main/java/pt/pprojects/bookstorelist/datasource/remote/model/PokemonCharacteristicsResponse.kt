package pt.pprojects.bookstorelist.datasource.remote.model

data class PokemonCharacteristicsResponse(
    val id: Int,
    val name: String,
    val base_experience: Int,
    val types: List<PokemonTypeResponse>,
    val height: Int,
    val weight: Int,
    val moves: List<PokemonMoveResponse>,
    val abilities: List<PokemonAbilityResponse>,
    val sprites: PokemonSpriteResponse
)