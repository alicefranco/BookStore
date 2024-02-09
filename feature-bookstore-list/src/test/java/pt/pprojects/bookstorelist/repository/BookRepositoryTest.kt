package pt.pprojects.bookstorelist.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import pt.pprojects.bookstorelist.data.datasource.BookCacheDataSourceInterface
import pt.pprojects.bookstorelist.data.datasource.BookRemoteDataSourceInterface
import pt.pprojects.bookstorelist.data.repository.BookRepository
import pt.pprojects.network.error.NetworkingError
import pt.pprojects.bookstorelist.domain.model.*

class BookRepositoryTest {
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    private val remoteDataSource: BookRemoteDataSourceInterface =
        mock(BookRemoteDataSourceInterface::class.java)
    private val cacheDataSource: BookCacheDataSourceInterface =
        mock(BookCacheDataSourceInterface::class.java)
    private lateinit var bookRepository: BookRepository

    @Before
    fun `before each test`() {
        bookRepository = BookRepository(
            cacheDataSource,
            remoteDataSource
        )
    }

    @Test
    fun `repository get book should return book`() {
        `when`(
            cacheDataSource
                .getBook("abcd")
        ).thenReturn(Maybe.just(book1))

        val result = bookRepository.getBook("abcd")

        assertThat(result).isEqualToComparingFieldByField(Maybe.just(expectedBook1))
    }

    @Test
    fun `repository get books should return books`() {
        `when`(
            remoteDataSource
                .getBooks(0)
        ).thenReturn(Single.just(booksDomain))

        val result = bookRepository.getBooks(0)

        assertThat(result).isEqualToComparingFieldByField(Single.just(expectedBooksDomain))
    }

    @Test
    fun `repository get favourite books should return favourite books`() {
        `when`(
            cacheDataSource
                .getFavouriteBooks()
        ).thenReturn(Single.just(favouriteBooksDomain))

        val result = bookRepository.getFavouriteBooks()

        assertThat(result).isEqualToComparingFieldByField(Single.just(expectedFavouriteBooksDomain))
    }

    @Test
    fun `repository mark as favourite books should return completable`() {
        `when`(
            cacheDataSource
                .markAsFavorite(book1)
        ).thenReturn(Completable.complete())

        val result = bookRepository.markAsFavourite(book1)

        assertThat(result).isEqualTo(Completable.complete())
    }

    @Test
    fun `repository remove favourite books should return completable`() {
        `when`(
            cacheDataSource
                .removeFavorite(book1)
        ).thenReturn(Completable.complete())

        val result = bookRepository.removeFavourite(book1)

        assertThat(result).isEqualTo(Completable.complete())
    }

    @Test
    fun `repository get books should return error`() {
        `when`(
            remoteDataSource
                .getBooks(0)
        ).thenReturn(Single.error(NetworkingError.ConnectionTimeout))

        val testObserver = bookRepository.getBooks( 0).test()

        testObserver
            .assertNoValues()
            .assertNotComplete()
            .assertError(NetworkingError.ConnectionTimeout)
    }


    private val book1 = Book(
        authors = listOf("Author1", "Author2"),
        title = "Title",
        id = "abcdefg1234567",
        image = "http://www.google.com/thumbnail1.png",
        description = "Description",
        buyLink = "http://www.google.com/link1"
    )

    private val book2 = Book(
        authors = listOf("Author3", "Author4"),
        title = "Another title",
        id = "abcdefg1234567",
        image =  "http://www.google.com/thumbnail2.png",
        description = "Another description",
        buyLink = "http://www.google.com/link2"
    )

    private val book3 = Book(
        authors = listOf("Author5", "Author6"),
        title = "Even another title",
        id = "abcdefg1234567",
        image =  "http://www.google.com/thumbnail3.png",
        description = "Even another description",
        buyLink = "http://www.google.com/link3"
    )

    private val expectedBook1 = Book(
        authors = listOf("Author1", "Author2"),
        title = "Title",
        id = "abcdefg1234567",
        image = "http://www.google.com/thumbnail1.png",
        description = "Description",
        buyLink = "http://www.google.com/link1"
    )


    private val booksDomain = listOf(
        book1,
        book2
    )

    private val expectedBooksDomain = listOf(
        book1,
        book2
    )

    private val favouriteBooksDomain = listOf(
        book1,
        book2,
        book3
    )

    private val expectedFavouriteBooksDomain = listOf(
        book1,
        book2,
        book3
    )


}