package com.jairorr.samplesocialmediajc.network

import retrofit2.HttpException
import retrofit2.Response

sealed class SocialMediaResult<T:Any> {
    class Success<T:Any>(val data:T):SocialMediaResult<T>()
    class Error<T:Any>(val code:Int, val message:String?):SocialMediaResult<T>()
    class Exception<T:Any>(val e:Throwable):SocialMediaResult<T>()
}

suspend fun <T:Any> handleApi(
    execute: suspend ()->Response<T>
):SocialMediaResult<T>{
    return try {
        val response = execute()
        val body = response.body()
        if(response.isSuccessful && body!=null){
            SocialMediaResult.Success(body)
        }else{
            SocialMediaResult.Error(code = response.code(), message = response.message())
        }
    }catch (e:HttpException){
        SocialMediaResult.Error(code = e.code(),message = e.message())
    }catch (e:Throwable){
        SocialMediaResult.Exception(e)
    }
}