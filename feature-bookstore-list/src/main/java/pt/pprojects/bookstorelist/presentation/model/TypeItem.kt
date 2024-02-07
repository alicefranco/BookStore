package pt.pprojects.bookstorelist.presentation.model

data class TypeItem(
    val name: String,
    val image: Int
) : DetailItem(name)