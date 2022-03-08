package com.example.mandatoryassignment.repository

import android.content.ReceiverCallNotAllowedException
import com.example.mandatoryassignment.models.ResaleItem
import retrofit2.Call
import retrofit2.http.*

interface ResaleItemService {
    @GET("ResaleItems")
    fun getAllResaleItems(): Call<List<ResaleItem>>

    @GET("ResaleItems/{id}")
    fun getResaleItemById(@Path("id") id: Int):Call<ResaleItem>

    @POST("ResaleItems")
    fun saveResaleItem(@Body resaleItem: ResaleItem): Call<ResaleItem>

    @DELETE("ResaleItems/{id}")
    fun deleteResaleItem(@Path("id") id: Int): Call<ResaleItem>
}