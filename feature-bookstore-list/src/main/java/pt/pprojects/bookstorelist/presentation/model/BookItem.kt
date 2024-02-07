package pt.pprojects.bookstorelist.presentation.model

data class BookItem(
    override var itemType: String = "BOOK_ITEM",
    var authors: List<String>,
    var title: String,
    var image: String
) : ListItem(itemType)