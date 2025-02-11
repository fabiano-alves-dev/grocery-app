package com.semblanceoffunctionality.grocery.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey @ColumnInfo(name = "name") var name: String,
    var wanted: Boolean = true,
    var quantity: Int = 0,
    var obtained: Boolean = false
) {
    fun quantityString(): String {
        if(wanted) {
            return quantity.toString()
        }
        return ""
    }

    override fun toString() = name
}
