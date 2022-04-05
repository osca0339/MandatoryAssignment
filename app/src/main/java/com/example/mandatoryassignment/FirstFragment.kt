package com.example.mandatoryassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mandatoryassignment.databinding.FragmentFirstBinding
import com.example.mandatoryassignment.models.MyAdapter
import com.example.mandatoryassignment.models.ResaleItemsViewModel

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val resaleItemsViewModel: ResaleItemsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resaleItemsViewModel.resaleItemsLiveData.observe(viewLifecycleOwner) { resaleItems ->
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (resaleItems == null) View.GONE else View.VISIBLE
            if(resaleItems != null) {
                val adapter = MyAdapter(resaleItems) { /*position ->
                    val item = resaleItems[position]
                    val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(item.title, item.description, item.price, item.seller, item.date)
                    findNavController().navigate(action)*/
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
            binding.swiperefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}