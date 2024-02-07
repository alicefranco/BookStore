package pt.pprojects.bookstorelist.domain.repository

import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Pokemon
import pt.pprojects.bookstorelist.domain.model.PokemonCharacteristics

interface BookRepositoryInterface {
    fun getPokemons(refresh: Boolean = false, offset: Int): Single<List<Pokemon>>
    fun getPokemonCharacteristics(refresh: Boolean = false, pokemonId: Int): Single<PokemonCharacteristics>
}