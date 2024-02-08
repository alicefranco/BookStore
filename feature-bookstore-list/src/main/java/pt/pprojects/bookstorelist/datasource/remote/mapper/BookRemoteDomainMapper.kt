package pt.pprojects.bookstorelist.datasource.remote.mapper

import pt.pprojects.bookstorelist.datasource.remote.model.*
import pt.pprojects.bookstorelist.domain.model.*

class BookRemoteDomainMapper {
    fun mapBooksToDomain(
        bookResponse: List<BookResponse>
    ): List<Book> {
        return bookResponse.map {
            Book(
                id = it.id,
                authors = it.volumeInfo?.authors ?: emptyList(),
                image = it.volumeInfo?.imageLinks?.thumbnail ?: "",
                title = it.volumeInfo?.title ?: "",
                description = it.volumeInfo?.description ?: "",
                buyLink = it.saleInfo?.buyLink ?: ""
            )
        }
    }
}