package pt.pprojects.bookstorelist.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pt.pprojects.bookstorelist.presentation.mapper.BookDomainPresentationMapper
import pt.pprojects.bookstorelist.presentation.bookstorelist.BookListViewModel
import pt.pprojects.bookstorelist.presentation.bookdetails.BookDetailsViewModel

val bookListPresentationModule = module {
    factory { BookDomainPresentationMapper() }

    viewModel {
        BookListViewModel(
            scheduler = get(),
            booksUseCase = get(),
            bookMapper = get()
        )
    }

    viewModel {
        BookDetailsViewModel(
            //favoritesUseCase = get(),
        )
    }
}