package pt.pprojects.bookstorelist.domain.usecase

import io.reactivex.rxjava3.core.Single
import pt.pprojects.domain.UseCaseInterface
import pt.pprojects.bookstorelist.domain.model.Pokemon
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface

class BooksUseCase(
    private val pokemonRepository: BookRepositoryInterface
) : UseCaseInterface<Single<List<Pokemon>>, Int> {

    override fun execute(refresh: Boolean, params: Int): Single<List<Pokemon>> {
        return pokemonRepository.getPokemons(refresh, offset = params)
    }
}
