package pt.pprojects.bookstorelist.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Book

interface BookRepositoryInterface {
    fun getBook(id: String): Maybe<Book>
    fun getBooks(startIndex: Int): Single<List<Book>>
    fun getFavouriteBooks(): Single<List<Book>>

    fun markAsFavourite(book: Book): Completable
    fun removeFavourite(book: Book): Completable
}