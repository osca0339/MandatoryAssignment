package com.example.mandatoryassignment.models

data class ResaleItem(val id: Int, val title: String, val description: String, val price: Int, val seller: String, val date: Int, val pictureUrl: String) {
    constructor(title: String, description: String, price: Int) : this(-1, title, description, price, "", -1, "" )

    override fun toString(): String {
        return "$title, $description, $price"
    }
}