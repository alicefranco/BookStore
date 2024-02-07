package pt.pprojects.bookstorelist.data.datasource

import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Pokemon

interface BookCacheDataSourceInterface {
    fun getPokemons(): Single<List<Pokemon>>
    fun getPokemonCharacteristics(pokemonId: Int): Single<Pokemon>
}