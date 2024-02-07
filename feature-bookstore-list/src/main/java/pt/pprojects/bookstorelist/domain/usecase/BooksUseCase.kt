package pt.pprojects.bookstorelist.domain.usecase

import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.domain.UseCaseInterface
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface

class BooksUseCase(
    private val bookRepository: BookRepositoryInterface
) : UseCaseInterface<Single<List<Book>>, Int> {

    override fun execute(refresh: Boolean, params: Int): Single<List<Book>> {
        return bookRepository.getBooks(refresh, params)
    }
}
