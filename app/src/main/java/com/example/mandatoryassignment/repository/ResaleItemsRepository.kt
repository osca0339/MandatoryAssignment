package com.example.mandatoryassignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mandatoryassignment.models.ResaleItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResaleItemsRepository {
    private val url = "https://anbo-restresale.azurewebsites.net/api/"

    private val resaleItemService: ResaleItemService
    val resaleItemsLiveData: MutableLiveData<List<ResaleItem>> = MutableLiveData<List<ResaleItem>>()
    val errorMessageLiveData:  MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        resaleItemService = build.create(ResaleItemService::class.java)
        getPosts()
    }

    fun getPosts() {
        resaleItemService.getAllResaleItems().enqueue(object : Callback<List<ResaleItem>> {
            override fun onResponse(call: Call<List<ResaleItem>>, response: Response<List<ResaleItem>>) {
                if (response.isSuccessful) {
                    //Log.d("APPLE", response.body().toString())
                    resaleItemsLiveData.postValue(response.body())
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<ResaleItem>>, t: Throwable) {
                //booksLiveData.postValue(null)
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun add(resaleItem: ResaleItem) {
        resaleItemService.saveResaleItem(resaleItem).enqueue(object : Callback<ResaleItem> {
            override fun onResponse(call: Call<ResaleItem>, response: Response<ResaleItem>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<ResaleItem>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun delete(id: Int) {
        resaleItemService.deleteResaleItem(id).enqueue(object : Callback<ResaleItem> {
            override fun onResponse(call: Call<ResaleItem>, response: Response<ResaleItem>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<ResaleItem>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }
}