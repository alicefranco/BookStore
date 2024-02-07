package pt.pprojects.bookstorelist.presentation.bookdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import pt.pprojects.bookstorelist.domain.usecase.BookDetailsUseCase
import pt.pprojects.bookstorelist.presentation.mapper.BookDomainPresentationMapper
import pt.pprojects.bookstorelist.presentation.model.PokemonDetails
import pt.pprojects.domain.Result

class BookDetailsViewModel(
    private val scheduler: Scheduler,
    private val pokemonCharacteristicsUseCase: BookDetailsUseCase,
    private val pokemonMapper: BookDomainPresentationMapper
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val mutablePokemonDetails =
        MutableLiveData<Result<PokemonDetails>>()
    val pokemonDetails: LiveData<Result<PokemonDetails>>
        get() = mutablePokemonDetails

    fun getPokemonDetails(pokemonId: Int) {
        val disposable = pokemonCharacteristicsUseCase.execute(refresh = false, params = pokemonId)
            .subscribeOn(scheduler)
            .doOnSubscribe { mutablePokemonDetails.postValue(Result.Loading) }
            .map<Result<PokemonDetails>> { pokemonDetails ->
                Result.Success(
                    pokemonMapper.mapPokemonDetailsToPresentation(pokemonDetails)
                )
            }
            .onErrorReturn { err -> Result.Error(err) }
            .subscribe(mutablePokemonDetails::postValue)

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
