package com.jairorr.samplesocialmediajc.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitObject {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://opencollective.com/webpack/members/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(SocialMediaService::class.java)
}