package com.example.mandatoryassignment.models

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mandatoryassignment.repository.AuthAppRepository
import com.google.firebase.auth.FirebaseUser

class AuthAppViewModel: ViewModel() {
    private val repository = AuthAppRepository(application = Application())
    private val userLiveData: FirebaseUser? = repository.getUserLiveDataMethod()
    //private val loggedOutLiveData: MutableLiveData<Boolean> = repository.getLoggedOutLiveDataMethod()

    fun login(email: String, password: String) {
        repository.login(email, password)
    }

    fun register(email: String, password: String) {
        repository.register(email, password)
    }

    fun getUserLiveData(): FirebaseUser? {
        return userLiveData
    }

    fun logOut() {
        repository.logOut()
    }

    /*fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }*/

    /*fun getCurrentEmail(): String? {
        return getCurrentEmail()
    }*/

}