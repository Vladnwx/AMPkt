package com.amp.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amp.R


class TableRowAdapter: RecyclerView.Adapter<TableRowAdapter.TableRowViewHolder> (){

    private var tableRowList = emptyList<TableRowModel>()

      class TableRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableRowViewHolder {

        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        when (viewType){
            TableRowModel.Text -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
               // return TableRowViewHolder(view)
            }
            TableRowModel.EditText -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_text_edittext, parent, false)
              //  return TableRowViewHolder(view)
            }
            TableRowModel.Spinner -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_spinner, parent, false)
             //   return TableRowViewHolder(view)
            }
            TableRowModel.Switch -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_switch, parent, false)
             //   return TableRowViewHolder(view)
            }

            TableRowModel.Buttton -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_button, parent, false)
                            }
                            }
        return TableRowViewHolder(view)

    }

    override fun onBindViewHolder(holder: TableRowViewHolder, position: Int) {
        when (tableRowList[position].viewType) {
            TableRowModel.Text -> {
                holder.itemView.findViewById<TextView>(R.id.textViewRow).text =
                    tableRowList[position].title
                holder.itemView.findViewById<TextView>(R.id.textViewRowValue).text =
                    tableRowList[position].titleValue
            }
            TableRowModel.EditText -> {
                holder.itemView.findViewById<TextView>(R.id.editTextViewRow).text =
                    tableRowList[position].title
                holder.itemView.findViewById<EditText>(R.id.editTextViewRow).setText(tableRowList[position].titleValue)

            }
            TableRowModel.Spinner -> {
            holder.itemView.findViewById<TextView>(R.id.textViewRow).text =
                tableRowList[position].title
            holder.itemView.findViewById<TextView>(R.id.textViewRowValue).text =
                tableRowList[position].titleValue
        }
            TableRowModel.Switch -> {
                holder.itemView.findViewById<TextView>(R.id.textViewRow).text =
                    tableRowList[position].title
                holder.itemView.findViewById<TextView>(R.id.textViewRowValue).text =
                    tableRowList[position].titleValue
            }
            TableRowModel.Buttton -> {
                holder.itemView.findViewById<TextView>(R.id.textViewRow).text =
                    tableRowList[position].title
                holder.itemView.findViewById<TextView>(R.id.textViewRowValue).text =
                    tableRowList[position].titleValue
            }


        }
    }
    override fun getItemCount(): Int {
        return tableRowList.size
    }
     fun setList(list: List<TableRowModel>) {
        tableRowList = list
    }
}