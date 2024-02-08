package pt.pprojects.bookstorelist.data.di

import org.koin.dsl.module
import pt.pprojects.bookstorelist.data.repository.BookRepository
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface

val bookListDataModule = module {
    single<BookRepositoryInterface> {
        BookRepository(
            cacheDataSource = get(),
            remoteDataSource = get()
        )
    }
}