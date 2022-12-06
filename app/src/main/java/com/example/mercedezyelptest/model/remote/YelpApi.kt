package com.example.mercedezyelptest.model.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpApi {
    @GET(ENDPOINT)
    suspend fun getBusinessByLocation(
        @Query(SEARCH_ARG_LAT)
        lat: Double,
        @Query(SEARCH_ARG_LON)
        lon: Double,
        @Query(SEARCH_ARG_LIMIT)
        limit: Int = 20): Response<BusinessResponse>
}