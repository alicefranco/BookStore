package pt.pprojects.bookstorelist.presentation.model

data class PokemonItem(
    override var itemType: String = "POKEMON_ITEM",
    var number: String,
    var name: String,
    var image: String
) : ListItem(itemType)