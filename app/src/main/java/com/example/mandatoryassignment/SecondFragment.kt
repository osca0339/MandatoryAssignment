package com.example.mandatoryassignment

import android.os.Bundle
import android.text.format.DateFormat.format
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mandatoryassignment.databinding.FragmentSecondBinding
import com.example.mandatoryassignment.models.ResaleItem
import com.example.mandatoryassignment.models.ResaleItemsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Long.parseLong
import java.lang.NullPointerException
import java.text.DateFormat


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentSecondBinding? = null
    private val args: SecondFragmentArgs by navArgs()
    private val resaleItemsViewModel: ResaleItemsViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.secondId.text = "Id: " + args.id
        binding.secondTitle.text = "Title: " + args.title
        binding.secondDescription.text = "Description: " + args.description
        binding.secondPrice.text = "Price: " + args.price + "DKK"
        binding.secondSeller.text = "Seller: " + args.seller
        val date = convertDate(args.date.toString(), "dd/MM/yyyy hh:mm:ss")
        binding.secondDate.text = "Resale item created on: " + date

        binding.deleteItemButton.setOnClickListener {
            try {
                if(auth.currentUser!!.email == args.seller) {
                    resaleItemsViewModel.delete(args.id)
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                } else {
                    binding.errorView.text = "You cannot delete this item unless you are the one who created it!"
                }
            } catch (e: NullPointerException) {
                Toast.makeText(context, "You cannot delete this item!",
                    Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }

        }

        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
    }

    fun convertDate(dateinMillis: String, dateFormat: String): String {
        return format(dateFormat, parseLong(dateinMillis)).toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}