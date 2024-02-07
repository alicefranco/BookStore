package pt.pprojects.bookstorelist.domain.repository

import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.bookstorelist.domain.model.PokemonCharacteristics

interface BookRepositoryInterface {
    fun getBooks(refresh: Boolean = false, startIndex: Int): Single<List<Book>>
    fun getPokemonCharacteristics(refresh: Boolean = false, pokemonId: Int): Single<PokemonCharacteristics>
}