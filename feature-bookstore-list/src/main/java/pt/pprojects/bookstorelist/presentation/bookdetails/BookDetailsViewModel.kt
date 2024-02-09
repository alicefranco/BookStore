package pt.pprojects.bookstorelist.presentation.bookdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.bookstorelist.domain.usecase.BooksUseCase

class BookDetailsViewModel(
    private val scheduler: Scheduler,
    private val booksUseCase: BooksUseCase
) : ViewModel() {

    private val mutableIsFavourite =
        MutableLiveData<Boolean>()
    val isFavourite: LiveData<Boolean>
        get() = mutableIsFavourite

    private val compositeDisposable = CompositeDisposable()
    fun isFavourite(id: String) {
        val disposable = booksUseCase.getBook(id)
            .subscribeOn(scheduler)
            .doOnEvent { book, throwable ->
                val isFavourite = book?.let { true } ?: false
                mutableIsFavourite.postValue(isFavourite)
            }
            .subscribe()

        compositeDisposable.add(disposable)
    }
    fun markAsFavorite(book: Book) {
        val disposable = booksUseCase.markAsFavourite(book)
            .subscribeOn(scheduler)
            .subscribe()

        compositeDisposable.add(disposable)
        mutableIsFavourite.postValue(true)
    }

    fun removeFavourite(book: Book) {
        val disposable = booksUseCase.removeFavourite(book)
            .subscribeOn(scheduler)
            .subscribe()

        compositeDisposable.add(disposable)
        mutableIsFavourite.postValue(false)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
