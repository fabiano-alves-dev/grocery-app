package com.semblanceoffunctionality.grocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.databinding.ListItemGroceryItemingBinding
import com.semblanceoffunctionality.grocery.ui.grocerylist.GroceryFragmentDirections
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
        init {
            binding.setClickListener {
                binding.viewModel?.let { viewModel ->
                    val action = GroceryFragmentDirections
                        .actionNavGroceriesToNavItemDetail(viewModel.name)
                    it.findNavController().navigate(action)
                }
            }
        }

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
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean {
        return oldItem == newItem
    }
}
