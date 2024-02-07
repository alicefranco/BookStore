package pt.pprojects.bookstorelist.domain.di

import org.koin.dsl.module
import pt.pprojects.bookstorelist.domain.usecase.BookDetailsUseCase
import pt.pprojects.bookstorelist.domain.usecase.BooksUseCase

val bookListDomainModule = module {
    factory { BooksUseCase(get()) }

    factory { BookDetailsUseCase(get()) }
}
