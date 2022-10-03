package com.amp.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        holder.itemView.findViewById<TextView>(R.id.textViewRow).text = tableRowList[position].title
        holder.itemView.findViewById<TextView>(R.id.textViewRowValue).text = tableRowList[position].titleValue

    }

    override fun getItemCount(): Int {
        return tableRowList.size
    }

     fun setList(list: List<TableRowModel>) {
        tableRowList = list
    }



}