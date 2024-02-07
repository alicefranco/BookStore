package pt.pprojects.bookstorelist.domain.usecase

import io.reactivex.rxjava3.core.Single
import pt.pprojects.domain.UseCaseInterface
import pt.pprojects.bookstorelist.domain.model.PokemonCharacteristics
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface

class BookDetailsUseCase(
    private val bookRepository: BookRepositoryInterface
) : UseCaseInterface<Single<PokemonCharacteristics>, Int> {

    override fun execute(refresh: Boolean, params: Int): Single<PokemonCharacteristics> {
        return bookRepository.getPokemonCharacteristics(pokemonId = params)
    }
}
