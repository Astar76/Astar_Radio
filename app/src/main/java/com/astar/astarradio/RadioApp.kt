package com.astar.astarradio

import android.app.Application
import android.util.Log
import com.astar.astarradio.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RadioApp : Application() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        scope.launch {
            val service = RetrofitClient.createRadioService()
            //val radioList = service.fetchRadioStations().list
            //Log.d("App", "onCreate() $radioList")

            val paramId = "radios/5"
            Log.d("App", "onCreate() ${service.fetchRadioStationById(paramId)}")
        }
    }
}