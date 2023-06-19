package com.jairorr.samplesocialmediajc.network

import com.jairorr.samplesocialmediajc.data.MemberUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SocialMediaService {
    @GET("users.json")
    suspend fun getRandomMembers(@Query("limit") limit:Int=15, @Query("offset") offset:Int = 0):Response<List<MemberUser>>
}