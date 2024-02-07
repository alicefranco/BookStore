package pt.pprojects.bookstorelist.data.repository

import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.data.datasource.BookRemoteDataSourceInterface
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.bookstorelist.domain.model.PokemonCharacteristics
import pt.pprojects.bookstorelist.domain.repository.BookRepositoryInterface

class BookRepository(
    // private val cacheDataSource: BookCacheDataSourceInterface,
    private val remoteDataSource: BookRemoteDataSourceInterface
) : BookRepositoryInterface {

    override fun getBooks(refresh: Boolean, startIndex: Int): Single<List<Book>> {
//        return when (refresh) {
//            false -> cacheDataSource.getPokemons()
//            true -> remoteDataSource.getPokemons(offset)
//        }
        return remoteDataSource.getBooks(startIndex)
    }

    override fun getPokemonCharacteristics(refresh: Boolean, pokemonId: Int): Single<PokemonCharacteristics> {
//        return when (refresh) {
//            false -> cacheDataSource.getPokemonCharacteristics(pokemonId)
//            true -> remoteDataSource.getPokemonCharacteristics(pokemonId)
//        }
        return remoteDataSource.getPokemonCharacteristics(pokemonId)
    }
}