package pt.pprojects.bookstorelist.datasource.remote.model

data class PokemonListResponse(
    val count: Int,
    val previous: String? = null,
    val next: String? = null,
    val results: List<PokemonResponse>
)