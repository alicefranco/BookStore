package pt.pprojects.bookstorelist.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.domain.Result
import pt.pprojects.network.error.NetworkingError
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface
import pt.pprojects.bookstorelist.domain.usecase.BooksUseCase
import pt.pprojects.bookstorelist.presentation.mapper.BookDomainPresentationMapper
import pt.pprojects.bookstorelist.presentation.bookstorelist.BookListViewModel
import pt.pprojects.bookstorelist.presentation.model.BookItem

class BookListViewModelTest {
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val bookRepository: BookRepositoryInterface =
        mock(BookRepositoryInterface::class.java)

    private lateinit var bookListUsecases: BooksUseCase
    private lateinit var bookListViewModel: BookListViewModel
    private val bookDomainPresentationMapper = BookDomainPresentationMapper()

    @Before
    fun `before each test`() {
        bookListUsecases = BooksUseCase(bookRepository)

        bookListViewModel = BookListViewModel(
            Schedulers.trampoline(),
            bookListUsecases,
            bookDomainPresentationMapper
        )
    }

    @Test
    fun `get books should return books`() {
        `when`(
            bookRepository.getBooks(0)
        ).thenReturn(Single.just(booksDomain))

        bookListViewModel.getBooks()

        assertThat(bookListViewModel.books.value).isEqualTo(
            Result.Success(
                expectedBookItems
            )
        )
    }

    @Test
    fun `get favourite books should return favourite books`() {
        `when`(
            bookRepository.getFavouriteBooks()
        ).thenReturn(Single.just(favouriteBooksDomain))

        bookListViewModel.getFavouriteBooks()

        assertThat(bookListViewModel.books.value).isEqualTo(
            Result.Success(
                expectedFavouriteBookItems
            )
        )
    }

    @Test
    fun `get books should return error`() {
        `when`(
            bookRepository.getBooks(0)
        ).thenReturn(Single.error(NetworkingError.ConnectionTimeout))
        bookListViewModel.getBooks()

        assertThat(bookListViewModel.books.value).isEqualToComparingFieldByField(
            Result.Error(
                NetworkingError.ConnectionTimeout
            )
        )
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

    private val bookItem1 = BookItem(
        authors = listOf("Author1", "Author2"),
        title = "Title",
        id = "abcdefg1234567",
        image = "http://www.google.com/thumbnail1.png",
        description = "Description",
        buyLink = "http://www.google.com/link1"
    )

    private val bookItem2 = BookItem(
        authors = listOf("Author3", "Author4"),
        title = "Another title",
        id = "abcdefg1234567",
        image =  "http://www.google.com/thumbnail2.png",
        description = "Another description",
        buyLink = "http://www.google.com/link2"
    )

    private val bookItem3 = BookItem(
        authors = listOf("Author5", "Author6"),
        title = "Even another title",
        id = "abcdefg1234567",
        image =  "http://www.google.com/thumbnail3.png",
        description = "Even another description",
        buyLink = "http://www.google.com/link3"
    )

    private val booksDomain = listOf(
        book1,
        book2
    )

    private val expectedBookItems = listOf(
        bookItem1,
        bookItem2
    )

    private val favouriteBooksDomain = listOf(
        book1,
        book2,
        book3
    )

    private val expectedFavouriteBookItems = listOf(
        bookItem1,
        bookItem2,
        bookItem3
    )

}
