package pt.pprojects.bookstorelist.mappers

import org.junit.Test

import org.assertj.core.api.Assertions.assertThat
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.bookstorelist.presentation.mapper.BookDomainPresentationMapper
import pt.pprojects.bookstorelist.presentation.model.*

class BookDomainPresentationMapperTest {

    private val bookDomainPresentationMapper = BookDomainPresentationMapper()

    @Test
    fun `should return domain books list`() {
        val result = bookDomainPresentationMapper
            .mapBooksToPresentation(booksDomain)

        assertThat(result).isEqualTo(expectedBooksPresentation)
    }

    @Test
    fun `should return empty list`() {
        val result = bookDomainPresentationMapper
            .mapBooksToPresentation(booksDomainEmpty)

        assertThat(result).isEqualTo(emptyList<BookItem>())
    }


    private val expectedBooksPresentation = listOf(
        BookItem(
            authors = listOf("Alice", "Bob"),
            title = "Alice in Wonderland",
            id = "abcdefg1234567",
            image = "http://www.google.com/image1",
            description = "Lorem ipsum",
            buyLink = "http://www.google.com/link1"
        ),
        BookItem(
            authors = listOf("Alice", "Bob"),
            title = "Unicorns",
            id = "abcdefg1234567",
            image = "http://www.google.com/image2",
            description = "Lorem ipsum",
            buyLink = "http://www.google.com/link2"
        )
    )

    private val booksDomain = listOf(
        Book(
            authors = listOf("Alice", "Bob"),
            title = "Alice in Wonderland",
            id = "abcdefg1234567",
            image = "http://www.google.com/image1",
            description = "Lorem ipsum",
            buyLink = "http://www.google.com/link1"
        ),
        Book(
            authors = listOf("Alice", "Bob"),
            title = "Unicorns",
            id = "abcdefg1234567",
            image = "http://www.google.com/image2",
            description = "Lorem ipsum",
            buyLink = "http://www.google.com/link2"
        )
    )

    private val booksDomainEmpty = listOf<Book>()


}
