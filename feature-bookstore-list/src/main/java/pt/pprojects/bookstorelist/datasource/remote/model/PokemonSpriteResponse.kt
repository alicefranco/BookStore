package pt.pprojects.bookstorelist.datasource.remote.model

data class PokemonSpriteResponse(
    val front_default: String,
    val front_female: String? = null,
    val back_default: String? = null,
    val back_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null
)
