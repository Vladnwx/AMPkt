package com.amp.RecyclerView

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.amp.MainActivity
import com.amp.R
import com.amp.databinding.ActivityExtendedBinding

class ActivityExtended : AppCompatActivity() {

    lateinit var binding: ActivityExtendedBinding

    lateinit var adapter : TableRowAdapter

    lateinit var recyclerView : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtendedBinding.inflate(layoutInflater)

        setContentView(binding.root)

        recyclerView = binding.recyclerViewActivityExtended
        adapter = TableRowAdapter()
        recyclerView.adapter = adapter
        adapter.setList(rowAdd())



        val buttonActivityMain =  findViewById<Button>(R.id.ButtonActivityMain)

        buttonActivityMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }
    }

    fun rowAdd () : ArrayList<TableRowModel>{

        val listRow = ArrayList<TableRowModel> ()

        var row1 = TableRowModel("Число фаз", "столбец 2")

        var row2 = TableRowModel("Мощность", "столбец 2")

        var row3 = TableRowModel("Кабель", "столбец 2")

        var row4 = TableRowModel("Расчетный ток", "столбец 2")

        var row5 = TableRowModel("Допустимый ток", "столбец 2")

        listRow.add(row1)
        listRow.add(row2)
        listRow.add(row3)
        listRow.add(row4)
        listRow.add(row5)

        return listRow
    }



}