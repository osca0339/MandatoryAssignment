package com.example.mandatoryassignment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mandatoryassignment.databinding.FragmentFirstBinding
import com.example.mandatoryassignment.models.AuthAppViewModel
import com.example.mandatoryassignment.models.MyAdapter
import com.example.mandatoryassignment.models.ResaleItemsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private val resaleItemsViewModel: ResaleItemsViewModel by activityViewModels()
    private val authAppViewModel: AuthAppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        authAppViewModel.getUserLiveData()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //.d("HEJ", authAppViewModel.getUserLiveData().toString())

        /*if (authAppViewModel.getUserLiveData() != null) {
            binding.loggedInAs.text = "Logged in as: "
            binding.toLoginFragment.text = "Logout"
            binding.toLoginFragment.setOnClickListener {
                Log.d("LOGIN", "logged out")
                authAppViewModel.logOut()
            }
        } else {
            binding.toLoginFragment.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_LoginRegistrationFragment)
            }
        }*/

        val currentUser = auth.currentUser
        if(currentUser != null) {
            binding.toLoginFragment.text = "Logout"
            binding.loggedInAs.text = "Logged in as: " + currentUser.email
            binding.toLoginFragment.setOnClickListener {
                auth.signOut()
                findNavController().navigate(R.id.refresh)
            }
        } else {
            binding.toLoginFragment.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_LoginRegistrationFragment)
            }
        }

        resaleItemsViewModel.resaleItemsLiveData.observe(viewLifecycleOwner) { resaleItems ->
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (resaleItems == null) View.GONE else View.VISIBLE
            if(resaleItems != null) {
                val adapter = MyAdapter(resaleItems) {
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }

        resaleItemsViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewFirst.text = errorMessage
        }

        resaleItemsViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            resaleItemsViewModel.reload()
            findNavController().navigate(R.id.refresh)
            binding.swiperefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}