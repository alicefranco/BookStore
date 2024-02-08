package pt.pprojects.bookstorelist.data.datasource

import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Book

interface BookRemoteDataSourceInterface {
    fun getBooks(startIndex: Int): Single<List<Book>>
}