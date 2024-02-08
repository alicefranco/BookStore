package pt.pprojects.bookstorelist.datasource.di

import org.koin.dsl.module
import pt.pprojects.bookstorelist.data.datasource.BookCacheDataSourceInterface
import pt.pprojects.bookstorelist.data.datasource.BookRemoteDataSourceInterface
import pt.pprojects.bookstorelist.datasource.local.BookCacheDataSource
import pt.pprojects.bookstorelist.datasource.remote.BookRemoteDataSource
import pt.pprojects.bookstorelist.datasource.remote.mapper.BookRemoteDomainMapper
import pt.pprojects.bookstorelist.datasource.remote.service.BookService
import retrofit2.Retrofit

val bookListDatasourceModule = module {
    single { get<Retrofit>().create(BookService::class.java) }

    single { BookRemoteDomainMapper() }

    single<BookRemoteDataSourceInterface> {
        BookRemoteDataSource(
            networkManager = get(),
            bookService = get(),
            bookMapper = get()
        )
    }

    single<BookCacheDataSourceInterface> {
        BookCacheDataSource(bookDao = get())
    }
}