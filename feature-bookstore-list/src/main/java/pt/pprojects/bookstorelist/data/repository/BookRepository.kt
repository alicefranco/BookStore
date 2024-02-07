package pt.pprojects.bookstorelist.data.repository

import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.domain.model.Pokemon
import pt.pprojects.bookstorelist.domain.model.PokemonCharacteristics
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface

class BookRepository(
    // private val cacheDataSource: PokemonCacheDataSourceInterface,
    private val remoteDataSource: pt.pprojects.bookstorelist.data.datasource.BookRemoteDataSourceInterface
) : BookRepositoryInterface {

    override fun getPokemons(refresh: Boolean, offset: Int): Single<List<Pokemon>> {
//        return when (refresh) {
//            false -> cacheDataSource.getPokemons()
//            true -> remoteDataSource.getPokemons(offset)
//        }
        return remoteDataSource.getPokemons(offset)
    }

    override fun getPokemonCharacteristics(refresh: Boolean, pokemonId: Int): Single<PokemonCharacteristics> {
//        return when (refresh) {
//            false -> cacheDataSource.getPokemonCharacteristics(pokemonId)
//            true -> remoteDataSource.getPokemonCharacteristics(pokemonId)
//        }
        return remoteDataSource.getPokemonCharacteristics(pokemonId)
    }
}