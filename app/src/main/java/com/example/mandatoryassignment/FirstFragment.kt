package com.example.mandatoryassignment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import com.example.mandatoryassignment.models.ResaleItem
import com.example.mandatoryassignment.models.ResaleItemsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

abstract class FirstFragment : Fragment() {
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

        //TODO this garbage
        var sortedList: List<ResaleItem>
        binding.sortingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                sortedList = sortItems()
            }
        }

        resaleItemsViewModel.resaleItemsLiveData.observe(viewLifecycleOwner) { resaleItems ->
            if(sortedList.isNotEmpty()) {
                binding.progressbar.visibility = View.GONE
                binding.recyclerView.visibility = if (sortedList == null) View.GONE else View.VISIBLE
                if(sortedList != null) {
                    val adapter = MyAdapter(sortedList) {
                    }
                    binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.recyclerView.adapter = adapter

                }
            }
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

    fun filterItems(criteria: String) {
        val filteredList = resaleItemsViewModel.resaleItemsLiveData
        when(criteria) {

        }
    }

    fun sortItems(): List<ResaleItem> {
        val sortedList: List<ResaleItem>
        sortedList = ArrayList()
        resaleItemsViewModel.resaleItemsLiveData to sortedList
        val spinner = binding.sortingSpinner.selectedItem as String
        when (spinner) {
            "Alphabetical Ascending" -> return sortedList.sortedBy { it.title }
            "Alphabetical Descending" -> return sortedList.sortedBy { it.title }
            "Price Ascending" -> return sortedList.sortedBy { it.price }
            "Price Descending" -> return sortedList.sortedByDescending { it.price }
            "Newest" -> return sortedList.sortedBy { it.date }
            "Oldest" -> return sortedList.sortedByDescending { it.date }
        }

        return sortedList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}