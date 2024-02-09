package pt.pprojects.bookstorelist.presentation.model

data class BookItem(
    val id: String,
    var authors: List<String>,
    var title: String,
    var image: String,
    var description: String,
    var buyLink: String
)