package pt.pprojects.bookstorelist.domain.usecase

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Book

interface BooksUseCaseInterface {
    fun getBooks(params: Int): Single<List<Book>>
    fun getFavoriteBooks(params: Int): Single<List<Book>>
    fun markAsFavourite(book: Book): Completable
    fun removeFavourite(book: Book): Completable
}