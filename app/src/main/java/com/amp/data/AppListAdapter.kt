package com.amp.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amp.R
import com.amp.data.entity.TypeOfEnvironment


class AppListAdapter : ListAdapter<TypeOfEnvironment, AppListAdapter.AppViewHolder>(AppComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.value)
    }


    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val typeOfEnvironmentItemView: TextView = itemView.findViewById(R.id.textViewRow)

        fun bind(text: String?) {
            typeOfEnvironmentItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): AppViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_main, parent, false)
                return AppViewHolder(view)
            }
        }
    }

    class AppComparator : DiffUtil.ItemCallback<TypeOfEnvironment>() {
        override fun areItemsTheSame(oldItem: TypeOfEnvironment, newItem: TypeOfEnvironment): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TypeOfEnvironment, newItem: TypeOfEnvironment): Boolean {
            return oldItem.value == newItem.value
        }
    }




}


