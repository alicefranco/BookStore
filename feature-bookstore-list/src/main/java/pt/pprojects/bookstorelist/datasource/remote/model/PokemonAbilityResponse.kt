package pt.pprojects.bookstorelist.datasource.remote.model

data class PokemonAbilityResponse(
    val ability: PokemonAbility,
    val is_hidden: Boolean
)

data class PokemonAbility(
    val name: String,
    val url: String
)
