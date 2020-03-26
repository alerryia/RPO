package alerryia.mtg

import retrofit2.http.GET

interface MtgApi {
    @GET("/v1/cards")
    suspend fun getCards(): Cards
}