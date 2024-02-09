package pt.pprojects.bookstorelist.mappers

import org.junit.Test

import pt.pprojects.bookstorelist.datasource.remote.mapper.BookRemoteDomainMapper
import org.assertj.core.api.Assertions.assertThat
import pt.pprojects.bookstorelist.datasource.remote.model.*
import pt.pprojects.bookstorelist.domain.model.Book

class BookRemoteDomainMapperTest {

    private val bookRemoteDomainMapper = BookRemoteDomainMapper()

    @Test
    fun `should return domain books list`() {
        val result = bookRemoteDomainMapper
            .mapBooksToDomain(booksResponse)

        assertThat(result).isEqualTo(expectedBooksDomain)
    }

    @Test
    fun `should return empty list`() {
        val result = bookRemoteDomainMapper
            .mapBooksToDomain(booksResponseEmpty)

        assertThat(result).isEqualTo(emptyList<Book>())
    }

    private val booksResponse = listOf(
        BookResponse(
            id = "abcdefg1234567",
            saleInfo = SaleInfoResponse(
                buyLink = "http://www.google.com/link1"
            ),
            volumeInfo = VolumeInfoResponse(
                title = "Title",
                authors = listOf("Author1", "Author2"),
                description = "Description",
                imageLinks = ImageResponse(
                    thumbnail = "http://www.google.com/thumbnail1.png"
                )
            )
        ),
        BookResponse(
            id = "abcdefg1234567",
            saleInfo = SaleInfoResponse(
                buyLink = "http://www.google.com/link2"
            ),
            volumeInfo = VolumeInfoResponse(
                title = "Title",
                authors = listOf("Author3", "Author4"),
                description = "Description",
                imageLinks = ImageResponse(
                    thumbnail = "http://www.google.com/thumbnail2.png"
                )
            )
        ),
    )

    private val expectedBooksDomain = listOf(
        Book(
            authors = listOf("Author1", "Author2"),
            title = "Title",
            id = "abcdefg1234567",
            image = "http://www.google.com/thumbnail1.png",
            description = "Description",
            buyLink = "http://www.google.com/link1"
        ),
        Book(
            authors = listOf("Author3", "Author4"),
            title = "Title",
            id = "abcdefg1234567",
            image =  "http://www.google.com/thumbnail2.png",
            description = "Description",
            buyLink = "http://www.google.com/link2"
        )
    )

    private val booksResponseEmpty = listOf<BookResponse>()
}
