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

        var s :String = getString(R.string.InsulationType)
        tableRowModelMap.put(s , "111")
        tableRowModelMap.put(getString(R.string.TypeOfEnvironment) , "1")
        tableRowModelMap.put(getString(R.string.NumberOfCore), "2")
        tableRowModelMap.put(getString(R.string.MaterialType), "3")
        tableRowModelMap.put(getString(R.string.InsulationType), "4")
        tableRowModelMap.put(getString(R.string.MethodOfLaying), "5")
        tableRowModelMap.put(getString(R.string.ConductorCrossSection), "6")
        tableRowModelMap.put(getString(R.string.CountPhase), "7")
        tableRowModelMap.put(getString(R.string.TypeAmperage), "8")
        tableRowModelMap.put(getString(R.string.cable), "9")
        tableRowModelMap.put(getString(R.string.EquivalentSection), "10")
        tableRowModelMap.put(getString(R.string.Resistance_r_ohm_km), "11")
        tableRowModelMap.put(getString(R.string.Resistance_x_ohm_km), "12")
        tableRowModelMap.put(getString(R.string.raschetniytok), "13")
        tableRowModelMap.put(getString(R.string.amperage), "14")
        tableRowModelMap.put(getString(R.string.Voltage), "15")
        tableRowModelMap.put(getString(R.string.cos_phi), "16")
        tableRowModelMap.put(getString(R.string.power), "17")
        tableRowModelMap.remove("0")

        tableRowModelMap.forEach() {
            var row = TableRowModel(it.key, it.value)
            listRow.add(row)
        }

        return listRow
    }



}