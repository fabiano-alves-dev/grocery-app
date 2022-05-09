package com.semblanceoffunctionality.grocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.databinding.ListItemGroceryItemingBinding
import com.semblanceoffunctionality.grocery.ui.grocerylist.GroceryWantedItemsViewModel

class GroceryWantedAdapter :
    ListAdapter<Item, GroceryWantedAdapter.ViewHolder>(
        GroceryItemDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_grocery_iteming,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemGroceryItemingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {}

        fun bind(item: Item) {
            with(binding) {
                viewModel = GroceryWantedItemsViewModel(item)
                executePendingBindings()
            }
        }
    }
}

private class GroceryItemDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean {
        return oldItem == newItem
    }
}
