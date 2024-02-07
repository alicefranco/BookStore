package pt.pprojects.bookstorelist.datasource.remote.service

import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.rxjava3.core.Single
import pt.pprojects.bookstorelist.datasource.remote.model.PokemonCharacteristicsResponse
import pt.pprojects.bookstorelist.datasource.remote.model.BookListResponse
import retrofit2.http.Path

interface BookService {
    @GET("volumes?q=mobile+development")
    fun getBooks(
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int
    ): Single<BookListResponse>

    @GET("pokemon/{pokemonId}")
    fun getPokemonCharacteristics(
        @Path("pokemonId") pokemonId: Int
    ): Single<PokemonCharacteristicsResponse>
}