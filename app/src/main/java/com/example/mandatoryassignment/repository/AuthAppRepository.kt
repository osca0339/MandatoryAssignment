package com.example.mandatoryassignment.repository

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.concurrent.timerTask

class AuthAppRepository {
    private var application: Application
    private var firebaseAuth: FirebaseAuth

    val userLiveData: MutableLiveData<FirebaseUser>
    val loggedOutLiveData: MutableLiveData<Boolean>

    constructor(application: Application) {
        this.application = application
        this.firebaseAuth = FirebaseAuth.getInstance()
        this.userLiveData = MutableLiveData()
        this.loggedOutLiveData = MutableLiveData()

        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser())
            loggedOutLiveData.postValue(false)
        }
    }

    fun getUserLiveDataMethod(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveDataMethod(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }


    fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    Toast.makeText(application, "Registration Failure: " + task.exception!!.message, Toast.LENGTH_SHORT).show();
                }
            }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    Toast.makeText(application, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }
}

