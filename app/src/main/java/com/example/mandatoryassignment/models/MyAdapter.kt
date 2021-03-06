package com.example.mandatoryassignment.models

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mandatoryassignment.FirstFragmentDirections
import com.example.mandatoryassignment.R
import com.example.mandatoryassignment.repository.AuthAppRepository
import com.google.firebase.auth.FirebaseUser

class MyAdapter(
    val items: List<ResaleItem>,
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
        viewHolder.textViewTitle.text = item.title
        viewHolder.textViewPrice.text = item.price.toString()
        viewHolder.detailsButton.text = "Details"


        viewHolder.detailsButton.setOnClickListener { position ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(item.id ,item.title, item.description, item.price, item.seller, item.date)
            findNavController(viewHolder.itemView).navigate(action)

        }
    }

    fun sort(sortBy: String, unsortedResaleItems: List<ResaleItem>) {
        //val unsortedResaleItems: List<ResaleItem> = items
        Log.d("HEJ", "Adapter")
        when(sortBy) {
            "Alphabetical Ascending" -> unsortedResaleItems.sortedBy { it.title }
            "Alphabetical Descending" -> unsortedResaleItems.sortedByDescending { it.title }
            "Price Ascending" -> unsortedResaleItems.sortedBy { it.price }
            "Price Descending" -> unsortedResaleItems.sortedByDescending { it.price }
            "Newest" -> unsortedResaleItems.sortedBy { it.date }
            "Oldest" -> unsortedResaleItems.sortedByDescending { it.date }
            else -> {
                unsortedResaleItems.sortedBy { it.title }
            }
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