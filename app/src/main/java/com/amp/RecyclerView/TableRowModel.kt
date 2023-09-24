package com.amp.RecyclerView

class TableRowModel () {
    var title: String = "1"
        get() = field
        set(value) {
            field = value
        }
    var titleValue: String = "1"
        get() = field
        set(value) {
            field = value
        }
  /*  enum class ViewType { Text, EditText, Spinner, Switch, Buttton }*/

    companion object {
        const val Text =1
        const val EditText =2
        const val Spinner =3
        const val Switch =4
        const val Buttton =5
    }
    var viewType = TableRowModel.Text
        get() = field
        set(value) {
            field = value
        }

    var spinnerList: MutableList<String> = mutableListOf<String>("0")
        get() = field
        set(value) {
            field = value
        }
    var switchDefault: Boolean = false
        get() = field
        set(value) {
            field = value
        }



}