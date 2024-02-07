package pt.pprojects.bookstorelist.datasource.remote

import io.reactivex.rxjava3.core.Single
import pt.pprojects.network.manager.NetworkManagerInterface
import pt.pprojects.bookstorelist.datasource.remote.mapper.BookRemoteDomainMapper
import pt.pprojects.bookstorelist.datasource.remote.service.BookService
import pt.pprojects.bookstorelist.domain.model.Pokemon
import pt.pprojects.bookstorelist.domain.model.PokemonCharacteristics

class BookRemoteDataSource(
    private val networkManager: NetworkManagerInterface,
    private val bookService: BookService,
    private val bookMapper: BookRemoteDomainMapper
) : pt.pprojects.bookstorelist.data.datasource.BookRemoteDataSourceInterface {
    override fun getPokemons(offset: Int): Single<List<Pokemon>> {
        return networkManager.performAndReturnsData(
            bookService
                .getPokemons(offset,
                    pt.pprojects.bookstorelist.datasource.remote.BookRemoteDataSource.Companion.limit
                )
                .map {
                    bookMapper.mapPokemonsToDomain(it.results)
                }
        )
    }

    override fun getPokemonCharacteristics(pokemonId: Int): Single<PokemonCharacteristics> {
        return networkManager.performAndReturnsData(
            bookService
                .getPokemonCharacteristics(pokemonId)
                .map {
                    bookMapper.mapPokemonCharacteristicsToDomain(it)
                }
        )
    }

    companion object {
        const val limit = 20
    }
}