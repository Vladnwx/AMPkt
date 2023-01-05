package com.amp.RecyclerView

import android.content.Intent
import android.content.res.Resources
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

    var tableRowModelMap = mutableMapOf("0" to "0")




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

        tableRowModelMap.put(R.string.TypeOfEnvironment.toString() , "1")
        tableRowModelMap.put(R.string.NumberOfCore.toString(), "2")
        tableRowModelMap.put(R.string.MaterialType.toString(), "3")
        tableRowModelMap.put(R.string.InsulationType.toString(), "4")
        tableRowModelMap.put(R.string.MethodOfLaying.toString(), "5")
        tableRowModelMap.put(R.string.ConductorCrossSection.toString(), "6")
        tableRowModelMap.put(R.string.CountPhase.toString(), "7")
        tableRowModelMap.put(R.string.TypeAmperage.toString(), "8")
        tableRowModelMap.put(R.string.cable.toString(), "9")
        tableRowModelMap.put(R.string.EquivalentSection.toString(), "10")
        tableRowModelMap.put(R.string.Resistance_r_ohm_km.toString(), "11")
        tableRowModelMap.put(R.string.Resistance_x_ohm_km.toString(), "12")
        tableRowModelMap.put(R.string.raschetniytok.toString(), "13")
        tableRowModelMap.put(R.string.amperage.toString(), "14")
        tableRowModelMap.put(R.string.Voltage.toString(), "15")
        tableRowModelMap.put(R.string.cos_phi.toString(), "16")
        tableRowModelMap.put(R.string.power.toString(), "17")

        tableRowModelMap.forEach() {
            var row = TableRowModel(it.key, it.value)
            listRow.add(row)
        }

        return listRow
    }



}