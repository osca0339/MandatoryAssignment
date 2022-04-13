package com.example.mandatoryassignment.models

data class ResaleItem(val id: Int, val title: String, val description: String, val price: Int, val seller: String, val date: Int, val pictureUrl: String) {
    constructor(id: Int, title: String, description: String, price: Int, date: Int, seller: String) : this(id, title, description, price, seller, date, "" )

    override fun toString(): String {
        return "$title $description $price"
    }
}