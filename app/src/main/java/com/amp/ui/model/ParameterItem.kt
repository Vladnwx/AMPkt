// com.amp.ui.model.ParameterItem.kt
package com.amp.ui.model

sealed class ParameterItem {
    abstract val key: String

    data class Text(override val key: String, val value: String) : ParameterItem()
    data class Spinner(override val key: String, val options: List<String>, val selected: Int) : ParameterItem()
    data class EditText(override val key: String, val value: String) : ParameterItem()
    data class Header(val title: String) : ParameterItem() { override val key: String = "" }
}