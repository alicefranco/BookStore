package pt.pprojects.bookstorelist.datasource.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.data.datasource.BookCacheDataSourceInterface
import pt.pprojects.bookstorelist.domain.model.Book

class BookCacheDataSource(
   val bookDao: BookDao
): BookCacheDataSourceInterface {
    override fun getFavouriteBooks(): Single<List<Book>> {
        return bookDao.getFavouriteBooks()
    }

    override fun markAsFavorite(book: Book): Completable {
        return bookDao.markAsFavorite(book)
    }

    override fun removeFavorite(book: Book): Completable {
        return bookDao.removeFavorite(book)
    }
}