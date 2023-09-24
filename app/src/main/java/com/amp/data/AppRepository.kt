package com.amp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.amp.data.dao.*
import com.amp.data.entity.*
import kotlinx.coroutines.flow.Flow

class AppRepository (private val typeOfEnvironmentDao: TypeOfEnvironmentDao,
                     private val typeAmperageDao: TypeAmperageDao,
                     private val resistivityDao: ResistivityDao,
                     private val numberOfCoreDao: NumberOfCoreDao,
                     private val nominalSizeDao: NominalSizeDao,
                     private val methodOfLayingDao: MethodOfLayingDao,
                     private val materialTypeDao: MaterialTypeDao,
                     private val insulationTypeDao: InsulationTypeDao,
                     private val amperageShortDao: AmperageShortDao,
                     private val amperageDao: AmperageDao
                     )

                    {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
                        val allTypeOfEnvironments: Flow<List<TypeOfEnvironment>> = typeOfEnvironmentDao.getAllFlow()
                        val allTypeAmperage:  Flow<List<TypeAmperage>> = typeAmperageDao.getAllFlow()
                        val allResistivity:   Flow<List<Resistivity>> = resistivityDao.getAllFlow()
                        val allNumberOfCore:  Flow<List<NumberOfCore>> = numberOfCoreDao.getAllFlow()
                        val allNominalSizes: Flow<List<NominalSize>> = nominalSizeDao.getAllFlow()
                        val allMethodOfLaying: Flow<List<MethodOfLaying>> = methodOfLayingDao.getAllFlow()
                        val allMaterialType: Flow<List<MaterialType>>  = materialTypeDao.getAllFlow()
                        val allInsulationType: Flow<List<InsulationType>> = insulationTypeDao.getAllFlow()



                        // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(typeOfEnvironment: TypeOfEnvironment) {
        typeOfEnvironmentDao.insert(typeOfEnvironment)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert( nominalSize: NominalSize) {
        nominalSizeDao.insert(nominalSize)
    }

     @Suppress("RedundantSuspendModifier")
     @WorkerThread
     suspend fun insert( resistivity: Resistivity) {
         resistivityDao.insert(resistivity)
                       }
     @Suppress("RedundantSuspendModifier")
     @WorkerThread
     suspend fun getDataFeeder(f: Feeder?) {
         if (f != null) {
             getR(f)
             getX(f)
             getAmperage(f)
             getAmperageShort(f)
         }

                        }




    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getR(materialType: String, nominalSize: Double): Double {
                            return resistivityDao.getR(materialType, nominalSize)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getR(f :Feeder): Double {
    return resistivityDao.getR(f.materialType, f.nominalSize)
                        }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getRLiveData(materialType: String, nominalSize: Double): LiveData<Double> {
        return resistivityDao.getLiveDataR (materialType, nominalSize)
    }

    @Suppress("RedundantSuspendModifier")
     @WorkerThread
    suspend fun getX(materialType: String, nominalSize: Double): Double {
        return resistivityDao.getX(materialType, nominalSize)
    }

                        @Suppress("RedundantSuspendModifier")
                        @WorkerThread
                        suspend fun getX(f: Feeder): Double {
                            return resistivityDao.getX(f.materialType, f.nominalSize)
                        }

                        @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAmperageShort(materialType: String, nominalSize: Double, insulationType : String): Double {
        return amperageShortDao.getAmperageShort(materialType, nominalSize, insulationType)
    }
                        @Suppress("RedundantSuspendModifier")
                        @WorkerThread
                        suspend fun getAmperageShort(f: Feeder): Double {
                            return amperageShortDao.getAmperageShort(f.materialType, f.nominalSize, f.insulationType)
                        }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAmperage(methodOfLaying: String, nominalSize: Double, materialType: String, insulationType : String, typeAmperage: String, numberOfCore: String, typeOfEnvironment: String): Double {
        return amperageDao.getAmperage(methodOfLaying, nominalSize, materialType, insulationType,typeAmperage, numberOfCore, typeOfEnvironment)
    }
                        @Suppress("RedundantSuspendModifier")
                        @WorkerThread
                        suspend fun getAmperage(f :Feeder): Double {
                            return amperageDao.getAmperage(f.methodOfLaying, f.nominalSize, f.materialType, f.insulationType,f.typeAmperage, f.numberOfCore, f.typeOfEnvironment)
                        }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getNominalSize(amperageCalculate: Double): Double {
      return amperageDao.getNominalSize(amperageCalculate)
     }

                        @Suppress("RedundantSuspendModifier")
                        @WorkerThread
                        suspend fun getAllNominalSizeList(): MutableList<Double> {
                            return nominalSizeDao.getAllListDouble()
                        }


}