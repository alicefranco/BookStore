package pt.pprojects.bookstorelist.data.datasource

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Book

interface BookCacheDataSourceInterface {
    fun getBook(id: String): Maybe<Book>
    fun getFavouriteBooks(): Single<List<Book>>

    fun markAsFavorite(book: Book): Completable

    fun removeFavorite(book: Book): Completable
}