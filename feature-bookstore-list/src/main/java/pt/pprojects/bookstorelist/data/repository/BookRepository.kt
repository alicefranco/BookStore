package pt.pprojects.bookstorelist.data.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.data.datasource.BookCacheDataSourceInterface
import pt.pprojects.bookstorelist.data.datasource.BookRemoteDataSourceInterface
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface

class BookRepository(
    private val cacheDataSource: BookCacheDataSourceInterface,
    private val remoteDataSource: BookRemoteDataSourceInterface
) : BookRepositoryInterface {
    override fun getBook(id: String): Maybe<Book> {
        return cacheDataSource.getBook(id)
    }

    override fun getBooks(startIndex: Int): Single<List<Book>> {
        return remoteDataSource.getBooks(startIndex)
    }

    override fun getFavouriteBooks(startIndex: Int): Single<List<Book>> {
        return cacheDataSource.getFavouriteBooks()
    }

    override fun markAsFavourite(book: Book): Completable{
        return cacheDataSource.markAsFavorite(book)
    }

    override fun removeFavourite(book: Book): Completable {
       return cacheDataSource.removeFavorite(book)
    }
}