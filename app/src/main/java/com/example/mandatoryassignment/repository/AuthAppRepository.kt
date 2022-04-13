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

    /*val userLiveData: FirebaseUser
    val loggedOutLiveData: MutableLiveData<Boolean>*/

    constructor(application: Application) {
        this.application = application
        this.firebaseAuth = FirebaseAuth.getInstance()
        /*this.userLiveData = FirebaseAuth.getInstance()
        this.loggedOutLiveData = MutableLiveData()

        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }*/
    }

    /*fun getUserLiveDataMethod(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveDataMethod(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }*/

    fun getUserLiveDataMethod(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun getCurrentEmail(): String? {
        return firebaseAuth.currentUser!!.email
    }


    fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    login(email, password)
                } else {
                    Toast.makeText(application, "Registration Failure: " + task.exception!!.message, Toast.LENGTH_SHORT).show();
                }
            }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    firebaseAuth.updateCurrentUser(firebaseAuth.currentUser!!)
                } else {
                    Toast.makeText(application, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun logOut() {
        firebaseAuth.signOut()
        getUserLiveDataMethod()
        //loggedOutLiveData.postValue(true)
    }
}

