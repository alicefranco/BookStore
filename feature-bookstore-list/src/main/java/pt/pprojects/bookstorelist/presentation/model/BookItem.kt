package pt.pprojects.bookstorelist.presentation.model

data class BookItem(
    override var itemType: String = "BOOK_ITEM",
    val id: String,
    var authors: List<String>,
    var title: String,
    var image: String,
    var description: String,
    var buyLink: String
) : ListItem(itemType)