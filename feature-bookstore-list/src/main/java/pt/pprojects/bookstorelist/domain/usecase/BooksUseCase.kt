package pt.pprojects.bookstorelist.domain.usecase

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface

class BooksUseCase(
    private val bookRepository: BookRepositoryInterface
): BooksUseCaseInterface {

    override fun getBook(id: String): Maybe<Book> {
        return bookRepository.getBook(id)
    }
    override fun getBooks(params: Int): Single<List<Book>> {
        return bookRepository.getBooks(params)
    }

    override fun getFavoriteBooks(params: Int): Single<List<Book>> {
        return bookRepository.getFavouriteBooks(params)
    }
    override fun markAsFavourite(book: Book): Completable {
        return bookRepository.markAsFavourite(book)
    }

    override fun removeFavourite(book: Book): Completable {
        return bookRepository.removeFavourite(book)
    }
}
