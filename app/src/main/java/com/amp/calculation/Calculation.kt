package com.amp.calculation

import android.icu.math.BigDecimal
import android.util.Log
import com.amp.data.ElectricalLoad
import com.amp.data.Feeder
import kotlin.math.roundToLong

 class Calculation {

     var amperageFormula : AmperageFormula = AmperageFormula()


    fun electricalLoad (e: ElectricalLoad?){
          if (e != null) {
          e.amperageCalculate =  amperageFormula.get(e.p*1000, e.v, e.cos, e.countPhase)
        }
                                             }

    fun feeder (f: Feeder?) {

                             }

    private fun automaticSelectionOfStandardVoltage (b :Boolean){



    }

}







