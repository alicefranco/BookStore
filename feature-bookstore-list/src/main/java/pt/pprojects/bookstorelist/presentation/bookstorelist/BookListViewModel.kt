package pt.pprojects.bookstorelist.presentation.bookstorelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import pt.pprojects.bookstorelist.domain.usecase.BooksUseCase
import pt.pprojects.domain.Result
import pt.pprojects.bookstorelist.presentation.mapper.BookDomainPresentationMapper
import pt.pprojects.bookstorelist.presentation.model.BookItem

class BookListViewModel(
    private val scheduler: Scheduler,
    private val booksUseCase: BooksUseCase,
    private val bookMapper: BookDomainPresentationMapper
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val PAGE_SIZE = 20
    private val START_OFFSET = 0
    private val TOTAL_BOOKS = 692

    private var offset = START_OFFSET

    var loadedAll = false

    private var listBooks: MutableList<BookItem> = mutableListOf()

    private var resultMutableBooks =
        MutableLiveData<Result<List<BookItem>>>()
    val books: LiveData<Result<List<BookItem>>>
        get() = resultMutableBooks

    var toggleFavorites = true

    fun getBooks() {
        toggleFavorites = true
        if (offset < TOTAL_BOOKS) {
            val disposable = booksUseCase.getBooks(offset)
                .subscribeOn(scheduler)
                .doOnSubscribe { resultMutableBooks.postValue(Result.Loading) }
                .map<Result<List<BookItem>>> { books ->
                    updateOffset()
                    val bookItems = bookMapper.mapBooksToPresentation(books)
                    listBooks.addAll(bookItems)
                    Result.Success(listBooks)
                }
                .onErrorReturn {
                    err -> Result.Error(err)
                }
                .subscribe(resultMutableBooks::postValue)

            compositeDisposable.add(disposable)
        }
    }

    fun getFavouriteBooks() {
        toggleFavorites = false
        val disposable = booksUseCase.getFavoriteBooks()
            .subscribeOn(scheduler)
            .doOnSubscribe { resultMutableBooks.postValue(Result.Loading) }
            .map<Result<List<BookItem>>> { books ->
                updateOffset()
                Result.Success(
                    bookMapper.mapBooksToPresentation(books)
                )
            }
            .onErrorReturn {
                    err -> Result.Error(err)
            }
            .subscribe(resultMutableBooks::postValue)

        compositeDisposable.add(disposable)

    }

    private fun updateOffset() {
        offset += PAGE_SIZE
        if (offset == TOTAL_BOOKS)
            loadedAll = true
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}