package pt.pprojects.bookstorelist.datasource.remote

import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.data.datasource.BookRemoteDataSourceInterface
import pt.pprojects.network.manager.NetworkManagerInterface
import pt.pprojects.bookstorelist.datasource.remote.mapper.BookRemoteDomainMapper
import pt.pprojects.bookstorelist.datasource.remote.service.BookService
import pt.pprojects.bookstorelist.domain.model.Book
import pt.pprojects.bookstorelist.domain.model.PokemonCharacteristics

class BookRemoteDataSource(
    private val networkManager: NetworkManagerInterface,
    private val bookService: BookService,
    private val bookMapper: BookRemoteDomainMapper
) : BookRemoteDataSourceInterface {
    override fun getBooks(startIndex: Int): Single<List<Book>> {
        return networkManager.performAndReturnsData(
            bookService
                .getBooks(startIndex,
                    maxResults
                )
                .map {
                    bookMapper.mapBooksToDomain(it.items)
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
        const val maxResults = 20
    }
}