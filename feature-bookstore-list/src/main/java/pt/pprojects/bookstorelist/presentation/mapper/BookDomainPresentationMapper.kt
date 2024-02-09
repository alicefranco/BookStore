package pt.pprojects.bookstorelist.presentation.mapper

import pt.pprojects.bookstorelist.domain.model.*
import pt.pprojects.bookstorelist.presentation.model.*

class BookDomainPresentationMapper {

    fun mapBooksToPresentation(
        books: List<Book>
    ): List<BookItem> {
        return books.map {
            BookItem(
                id = it.id,
                image = it.image,
                authors = it.authors,
                title = it.title,
                description = it.description,
                buyLink = it.buyLink
            )
        }
    }
}