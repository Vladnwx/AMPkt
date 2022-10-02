package com.amp.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amp.R

class TableRowAdapter: RecyclerView.Adapter<TableRowAdapter.TableRowViewHolder> (){

    private var tableRowList = emptyList<TableRowModel>()

    class TableRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableRowViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return TableRowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TableRowViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return tableRowList.size
    }


}