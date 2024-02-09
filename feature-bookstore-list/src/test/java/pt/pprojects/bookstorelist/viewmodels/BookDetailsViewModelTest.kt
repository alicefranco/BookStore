package pt.pprojects.bookstorelist.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface
import pt.pprojects.bookstorelist.domain.usecase.BooksUseCase
import pt.pprojects.bookstorelist.presentation.bookdetails.BookDetailsViewModel

class BookDetailsViewModelTest {
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val bookRepository: BookRepositoryInterface =
        mock(BookRepositoryInterface::class.java)

    private lateinit var booksUsecases: BooksUseCase
    private lateinit var bookDetailsViewModel: BookDetailsViewModel

    @Before
    fun `before each test`() {
        booksUsecases = BooksUseCase(bookRepository)

        bookDetailsViewModel = BookDetailsViewModel(
            Schedulers.trampoline(),
            booksUsecases
        )
    }

    @Test
    fun `isFavourite should return true`() {
        `when`(
            bookRepository.getBook("abcdefg1234567")
        ).thenReturn(Maybe.just(book))

        bookDetailsViewModel.isFavourite("abcdefg1234567")

        assertThat(bookDetailsViewModel.isFavourite.value)
            .isEqualTo(true)
    }

    @Test
    fun `markAsFavourite should return isFavourite as false`() {
        `when`(
            bookRepository.markAsFavourite(book)
        ).thenReturn(Completable.complete())

        bookDetailsViewModel.markAsFavorite(book)

        assertThat(bookDetailsViewModel.isFavourite.value)
            .isEqualTo(true)
    }

    @Test
    fun `removeFavorite should return isFavourite as false`() {
        `when`(
            bookRepository.removeFavourite(book)
        ).thenReturn(Completable.complete())

        bookDetailsViewModel.removeFavourite(book)

        assertThat(bookDetailsViewModel.isFavourite.value)
            .isEqualTo(false)
    }

    private val book = Book(
        authors = listOf("Author3", "Author4"),
        title = "Title",
        id = "abcdefg1234567",
        image =  "http://www.google.com/thumbnail2.png",
        description = "Description",
        buyLink = "http://www.google.com/link2"
    )

}