package com.example.mandatoryassignment.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mandatoryassignment.repository.ResaleItemsRepository

class ResaleItemsViewModel: ViewModel() {
    private val repository = ResaleItemsRepository()
    val resaleItemsLiveData: LiveData<List<ResaleItem>> = repository.resaleItemsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getPosts()
    }

    operator fun get(index: Int): ResaleItem? {
        return resaleItemsLiveData.value?.get(index)
    }

    fun add(resaleItem: ResaleItem) {
        repository.add(resaleItem)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }
}