package com.example.weatherapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org"
private const val API_KEY = "2fd93d34a2121d9ff3507a700d1b19ce"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface OneCallApiService {
    @GET("/data/2.5/onecall")
    fun getWeatherDetail(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") APP_ID: String = API_KEY
    ): Call<String>

    @GET("data/2.5/weather")
    fun getCurrentWeatherByCoordinatesAsync(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") APP_ID: String = API_KEY
    ): Deferred<CurrentWeatherApiResponse>

    @GET("data/2.5/weather")
    fun getCurrentWeatherByCityID(
        @Query("id") id: Int,
        @Query("appid") APP_ID: String = API_KEY
    ): Deferred<CurrentWeatherApiResponse>

}

object OneCallApi {
    val retrofitService: OneCallApiService by lazy {
        retrofit.create(OneCallApiService::class.java)
    }
}