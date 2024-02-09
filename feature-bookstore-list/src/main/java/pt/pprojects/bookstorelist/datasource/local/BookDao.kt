package pt.pprojects.bookstorelist.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Book

@Dao
interface BookDao {

    @Query("SELECT * FROM book WHERE id = :id")
    fun getBook(id: String): Maybe<Book>

    @Query("SELECT * FROM book")
    fun getFavouriteBooks(): Single<List<Book>>

    @Insert
    fun markAsFavorite(book: Book): Completable

    @Delete
    fun removeFavorite(book: Book): Completable
}