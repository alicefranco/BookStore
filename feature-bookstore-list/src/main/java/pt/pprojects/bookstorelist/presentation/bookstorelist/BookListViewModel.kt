package pt.pprojects.bookstorelist.presentation.bookstorelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import pt.pprojects.bookstorelist.domain.usecase.BooksUseCase
import pt.pprojects.domain.Result
import pt.pprojects.bookstorelist.presentation.mapper.BookDomainPresentationMapper
import pt.pprojects.bookstorelist.presentation.model.PokemonItem

class BookListViewModel(
    private val scheduler: Scheduler,
    private val booksUseCase: BooksUseCase,
    private val bookMapper: BookDomainPresentationMapper
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val PAGE_SIZE = 20
    private val START_OFFSET = 0
    private val TOTAL_POKEMONS = 984

    private var offset = START_OFFSET

    var loadedAll = false

    private val mutablePokemons =
        MutableLiveData<Result<List<PokemonItem>>>()
    val pokemons: LiveData<Result<List<PokemonItem>>>
        get() = mutablePokemons

    fun getPokemons(refresh: Boolean = false) {
        if (offset < TOTAL_POKEMONS) {
            val disposable = booksUseCase.execute(refresh, offset)
                .subscribeOn(scheduler)
                .doOnSubscribe { mutablePokemons.postValue(Result.Loading) }
                .map<Result<List<PokemonItem>>> { pokemons ->
                    updateOffset()
                    Result.Success(
                        bookMapper.mapPokemonsToPresentation(pokemons)
                    )
                }
                .onErrorReturn { err -> Result.Error(err) }
                .subscribe(mutablePokemons::postValue)

            compositeDisposable.add(disposable)
        }
    }

    private fun updateOffset() {
        offset += PAGE_SIZE
        if (offset == TOTAL_POKEMONS)
            loadedAll = true
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}