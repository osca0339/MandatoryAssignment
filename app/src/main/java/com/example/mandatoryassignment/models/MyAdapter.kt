package com.example.mandatoryassignment.models

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mandatoryassignment.FirstFragmentDirections
import com.example.mandatoryassignment.R
import com.example.mandatoryassignment.repository.AuthAppRepository
import com.google.firebase.auth.FirebaseUser

class MyAdapter(
    private val items: List<ResaleItem>,
    private val authAppViewModel: AuthAppViewModel,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)
        return MyViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val item = items[position]
        val currentUser = authAppViewModel.getUserLiveData()
        viewHolder.textViewTitle.text = item.title
        viewHolder.textViewPrice.text = item.price.toString()
        viewHolder.detailsButton.text = "Details"
        //item.seller = authAppViewModel.getUserLiveData().toString()
        //TODO Figure out how to get currentUser email

        viewHolder.detailsButton.setOnClickListener { position ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(item.title, item.description, item.price, item.seller, item.date)
            findNavController(viewHolder.itemView).navigate(action)

        }
    }

    class MyViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewTitle: TextView = itemView.findViewById(R.id.textview_list_item_title)
        val textViewPrice: TextView = itemView.findViewById(R.id.textview_list_item_price)
        val detailsButton: Button = itemView.findViewById(R.id.textview_list_item_details)

        init {
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            // gradle     implementation "androidx.recyclerview:recyclerview:1.2.1"
            onItemClicked(position)
        }
    }
}