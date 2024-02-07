package pt.pprojects.bookstorelist.domain.model

data class Book(
    var authors: List<String>,
    var title: String,
    var image: String
)