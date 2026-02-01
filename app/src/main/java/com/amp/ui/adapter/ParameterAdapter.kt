// com.amp.ui.adapter.ParameterAdapter.kt
package com.amp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amp.R
import com.amp.databinding.ParameterEdittextBinding
import com.amp.databinding.ParameterHeaderBinding
import com.amp.databinding.ParameterSpinnerBinding
import com.amp.databinding.ParameterTextBinding
import com.amp.ui.model.ParameterItem
import com.google.android.material.textfield.TextInputEditText

class ParameterAdapter(
    private val onEditTextChange: (key: String, value: String) -> Unit = { _, _ -> },
    private val onSpinnerSelect: (key: String, index: Int) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = listOf<ParameterItem>()

    fun updateList(newItems: List<ParameterItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ParameterItem.Text -> VIEW_TYPE_TEXT
            is ParameterItem.Spinner -> VIEW_TYPE_SPINNER
            is ParameterItem.EditText -> VIEW_TYPE_EDITTEXT
            is ParameterItem.Header -> VIEW_TYPE_HEADER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_TEXT -> TextViewHolder(ParameterTextBinding.inflate(inflater, parent, false))
            VIEW_TYPE_SPINNER -> SpinnerViewHolder(ParameterSpinnerBinding.inflate(inflater, parent, false))
            VIEW_TYPE_EDITTEXT -> EditTextViewHolder(ParameterEdittextBinding.inflate(inflater, parent, false))
            VIEW_TYPE_HEADER -> HeaderViewHolder(ParameterHeaderBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ParameterItem.Text -> (holder as TextViewHolder).bind(item)
            is ParameterItem.Spinner -> (holder as SpinnerViewHolder).bind(item)
            is ParameterItem.EditText -> (holder as EditTextViewHolder).bind(item)
            is ParameterItem.Header -> (holder as HeaderViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    // === ViewHolders ===

    class TextViewHolder(private val binding: ParameterTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParameterItem.Text) {
            binding.textViewKey.text = item.key
            binding.textViewValue.text = item.value
        }
    }

    class SpinnerViewHolder(private val binding: ParameterSpinnerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParameterItem.Spinner) {
            binding.textViewKey.text = item.key
            val adapter = ArrayAdapter(
                binding.root.context,
                android.R.layout.simple_spinner_item,
                item.options
            ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            binding.spinnerValue.adapter = adapter
            binding.spinnerValue.setSelection(item.selected)

            binding.spinnerValue.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                    // Передаём событие вверх
                    (binding.root.parent as? RecyclerView)?.adapter?.let { adapter ->
                        if (adapter is ParameterAdapter) {
                            adapter.onSpinnerSelect(item.key, pos)
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    class EditTextViewHolder(private val binding: ParameterEdittextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParameterItem.EditText) {
            binding.textViewKey.text = item.key
            binding.editTextValue.setText(item.value)

            binding.editTextValue.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val newValue = binding.editTextValue.text.toString()
                    (binding.root.parent as? RecyclerView)?.adapter?.let { adapter ->
                        if (adapter is ParameterAdapter) {
                            adapter.onEditTextChange(item.key, newValue)
                        }
                    }
                }
            }
        }
    }

    class HeaderViewHolder(private val binding: ParameterHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParameterItem.Header) {
            binding.textViewHeader.text = item.title
        }
    }

    companion object {
        const val VIEW_TYPE_TEXT = 0
        const val VIEW_TYPE_SPINNER = 1
        const val VIEW_TYPE_EDITTEXT = 2
        const val VIEW_TYPE_HEADER = 3
    }
}