package com.amp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.amp.data.Feeder
import com.amp.data.entity.Resistivity
import kotlinx.coroutines.flow.Flow


@Dao
interface ResistivityDao:BaseDAO<Resistivity>{



   @Query("SELECT * FROM Resistivity")
    fun getAllFlow(): Flow<List<Resistivity>>

    @Query("SELECT R  FROM resistivity WHERE materialType =:tMaterial_type AND nominalSize = :tNominal_size")
    fun getR(tMaterial_type: String, tNominal_size: Double): Double

    @Query("SELECT R  FROM resistivity WHERE materialType = :tMaterial_type AND nominalSize = :tNominal_size")
    fun getLiveDataR(tMaterial_type: String, tNominal_size: Double): LiveData<Double>

    @Query("SELECT X  FROM resistivity WHERE materialType = :tMaterial_type AND nominalSize = :tNominal_size")
    fun getX(tMaterial_type: String, tNominal_size: Double): Double

    @Query("SELECT X  FROM resistivity WHERE materialType = :tMaterial_type AND nominalSize = :tNominal_size")
    fun getLiveDataX(tMaterial_type: String, tNominal_size: Double): LiveData<Double>

    @Query("SELECT * FROM resistivity ORDER BY id ASC")
    fun getAlphabetized(): LiveData<List<Resistivity?>?>?

    @Query("DELETE FROM Resistivity")
    suspend fun deleteAll()

    @Query(
        """INSERT INTO Resistivity (
                            p,
                            X,
                            R,
                            nominalSize,
                            materialType,
                            id
                        )
                        VALUES (
                            0.0189,
                            0.06,
                            0.0189,
                            1000,
                            'Cu',
                            1
                        ),
                        (
                            0.0189,
                            0.06,
                            0.023625,
                            800,
                            'Cu',
                            2
                        ),
                        (
                            0.0189,
                            0.06,
                            0.03,
                            630,
                            'Cu',
                            3
                        ),
                        (
                            0.0189,
                            0.06,
                            0.0378,
                            500,
                            'Cu',
                            4
                        ),
                        (
                            0.0189,
                            0.06,
                            0.04725,
                            400,
                            'Cu',
                            5
                        ),
                        (
                            0.0189,
                            0.06,
                            0.063,
                            300,
                            'Cu',
                            6
                        ),
                        (
                            0.0189,
                            0.06,
                            0.07875,
                            240,
                            'Cu',
                            7
                        ),
                        (
                            0.0189,
                            0.06,
                            0.10216216216216,
                            185,
                            'Cu',
                            8
                        ),
                        (
                            0.0189,
                            0.06,
                            0.126,
                            150,
                            'Cu',
                            9
                        ),
                        (
                            0.0189,
                            0.06,
                            0.1575,
                            120,
                            'Cu',
                            10
                        ),
                        (
                            0.0189,
                            0.06,
                            0.19894736842105,
                            95,
                            'Cu',
                            11
                        ),
                        (
                            0.0189,
                            0.06,
                            0.27,
                            70,
                            'Cu',
                            12
                        ),
                        (
                            0.0189,
                            0.06,
                            0.378,
                            50,
                            'Cu',
                            13
                        ),
                        (
                            0.0189,
                            0.06,
                            0.54,
                            35,
                            'Cu',
                            14
                        ),
                        (
                            0.0189,
                            0.06,
                            0.756,
                            25,
                            'Cu',
                            15
                        ),
                        (
                            0.0189,
                            0.06,
                            1.18125,
                            16,
                            'Cu',
                            16
                        ),
                        (
                            0.0189,
                            0.06,
                            1.89,
                            10,
                            'Cu',
                            17
                        ),
                        (
                            0.0189,
                            0.06,
                            3.15,
                            6,
                            'Cu',
                            18
                        ),
                        (
                            0.0189,
                            0.06,
                            4.725,
                            4,
                            'Cu',
                            19
                        ),
                        (
                            0.0189,
                            0.06,
                            7.56,
                            2.5,
                            'Cu',
                            20
                        ),
                        (
                            0.0189,
                            0.06,
                            12.6,
                            1.5,
                            'Cu',
                            21
                        ),
                        (
                            0.0189,
                            0.06,
                            25.2,
                            0.75,
                            'Cu',
                            22
                        ),
                        (
                            0.0189,
                            0.06,
                            37.8,
                            0.5,
                            'Cu',
                            23
                        ),
                        (
                            0.0315,
                            0.06,
                            0.0315,
                            1000,
                            'Al',
                            24
                        ),
                        (
                            0.0315,
                            0.06,
                            0.039375,
                            800,
                            'Al',
                            25
                        ),
                        (
                            0.0315,
                            0.06,
                            0.05,
                            630,
                            'Al',
                            26
                        ),
                        (
                            0.0315,
                            0.06,
                            0.063,
                            500,
                            'Al',
                            27
                        ),
                        (
                            0.0315,
                            0.06,
                            0.07875,
                            400,
                            'Al',
                            28
                        ),
                        (
                            0.0315,
                            0.06,
                            0.105,
                            300,
                            'Al',
                            29
                        ),
                        (
                            0.0315,
                            0.06,
                            0.13125,
                            240,
                            'Al',
                            30
                        ),
                        (
                            0.0315,
                            0.06,
                            0.17027027027027,
                            185,
                            'Al',
                            31
                        ),
                        (
                            0.0315,
                            0.06,
                            0.21,
                            150,
                            'Al',
                            32
                        ),
                        (
                            0.0315,
                            0.06,
                            0.2625,
                            120,
                            'Al',
                            33
                        ),
                        (
                            0.0315,
                            0.06,
                            0.33157894736842,
                            95,
                            'Al',
                            34
                        ),
                        (
                            0.0315,
                            0.06,
                            0.45,
                            70,
                            'Al',
                            35
                        ),
                        (
                            0.0315,
                            0.06,
                            0.63,
                            50,
                            'Al',
                            36
                        ),
                        (
                            0.0315,
                            0.06,
                            0.9,
                            35,
                            'Al',
                            37
                        ),
                        (
                            0.0315,
                            0.06,
                            1.26,
                            25,
                            'Al',
                            38
                        ),
                        (
                            0.0315,
                            0.06,
                            1.96875,
                            16,
                            'Al',
                            39
                        ),
                        (
                            0.0315,
                            0.06,
                            3.15,
                            10,
                            'Al',
                            40
                        ),
                        (
                            0.0315,
                            0.06,
                            5.25,
                            6,
                            'Al',
                            41
                        ),
                        (
                            0.0315,
                            0.06,
                            7.875,
                            4,
                            'Al',
                            42
                        ),
                        (
                            0.0315,
                            0.06,
                            12.6,
                            2.5,
                            'Al',
                            43
                        ),
                        (
                            0.0315,
                            0.06,
                            21,
                            1.5,
                            'Al',
                            44
                        ),
                        (
                            0.0315,
                            0.06,
                            42,
                            0.75,
                            'Al',
                            45
                        ),
                        (
                            0.0315,
                            0.06,
                            63,
                            0.5,
                            'Al',
                            46
                        );
"""
    )
    fun defaultgreate()

}