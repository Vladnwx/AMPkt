// com.amp.ui.adapter.TableRowAdapter.kt
package com.amp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amp.R
import com.amp.ui.model.TableRowModel

class TableRowAdapter : RecyclerView.Adapter<TableRowAdapter.ViewHolder>() {

    private var items = emptyList<TableRowModel>()

    fun updateList(newItems: List<TableRowModel>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false) // ← твой существующий layout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.textViewRow)
        private val valueView: TextView = itemView.findViewById(R.id.textViewRowValue)

        fun bind(item: TableRowModel) {
            titleView.text = item.title
            valueView.text = item.value
        }
    }
}