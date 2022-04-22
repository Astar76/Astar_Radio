package com.astar.astarradio.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://a0577461.xsph.ru/api/radio/"

interface RadioService {

    @GET("index.php?q=radios")
    suspend fun fetchRadioStations(): RadioStationsCloud

    @GET("index.php?")
    suspend fun fetchRadioStationById(
        @Query("q") paramId: String,
    ): SimpleRadioStationCloud
}

object RetrofitClient {

    private val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    fun createRadioService(): RadioService {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
        val client = builder.build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(RadioService::class.java)
    }
}