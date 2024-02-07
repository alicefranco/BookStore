package pt.pprojects.bookstorelist.data.datasource

import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Pokemon
import pt.pprojects.bookstorelist.domain.model.PokemonCharacteristics

interface BookRemoteDataSourceInterface {
    fun getPokemons(offset: Int): Single<List<Pokemon>>
    fun getPokemonCharacteristics(pokemonId: Int): Single<PokemonCharacteristics>
}