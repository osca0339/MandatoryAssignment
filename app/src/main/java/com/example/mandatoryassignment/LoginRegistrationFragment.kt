package com.example.mandatoryassignment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mandatoryassignment.databinding.LoginRegistrationFragmentBinding
import com.example.mandatoryassignment.models.AuthAppViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginRegistrationFragment: Fragment() {
    private var _binding: LoginRegistrationFragmentBinding? = null
    private val authAppViewModel: AuthAppViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        _binding = LoginRegistrationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginRegistrationRegister.setOnClickListener {
            Log.d("HEJ", "wth")
            val emailInput = binding.loginRegistrationEmail
            val emailText = emailInput.text.toString()
            val passwordInput = binding.loginRegistrationPassword
            val passwordText = passwordInput.text.toString()

            Log.d("HEJ", emailText + passwordText)

            /*if (emailText.isEmpty() && passwordText.isEmpty()) {
                emailInput.error = "No input!"
                passwordInput.error = "No input!"
            }

            if (emailText.isEmpty()) {
                emailInput.error = "No input!"
            }

            if (passwordText.isEmpty()) {
                passwordInput.error = "No input!"
            }*/

            //authAppViewModel.register(emailText, passwordText)
            //Log.d("HEJ", authAppViewModel.register(emailText, passwordText).toString())
            auth.createUserWithEmailAndPassword(emailText, passwordText)
            auth.signInWithEmailAndPassword(emailText, passwordText)
            findNavController().navigate(R.id.action_LoginRegistrationFragment_to_FirstFragment)
        }

        binding.loginRegistrationLogin.setOnClickListener {
            Log.d("HEJ", "john")
            val emailInput = binding.loginRegistrationEmail
            val emailText = emailInput.text.toString()
            val passwordInput = binding.loginRegistrationPassword
            val passwordText = passwordInput.text.toString()

            Log.d("HEJ", emailText + passwordText)

            /*if (emailText.isEmpty() && passwordText.isEmpty()) {
                emailInput.error = "No input!"
                passwordInput.error = "No input!"
            }

            if (emailText.isEmpty()) {
                emailInput.error = "No input!"
            }

            if (passwordText.isEmpty()) {
                passwordInput.error = "No input!"
            }*/

            auth.signInWithEmailAndPassword(emailText, passwordText)
            findNavController().navigate(R.id.action_LoginRegistrationFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}