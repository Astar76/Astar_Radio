package com.astar.astarradio

import android.app.Application
import android.util.Log
import com.astar.astarradio.core.Result
import com.astar.astarradio.data.BaseRadioRepository
import com.astar.astarradio.data.RadioStationDomainMapper
import com.astar.astarradio.data.remote.RadioCloudDataSource
import com.astar.astarradio.data.remote.RetrofitClient
import com.astar.astarradio.domain.RadioRepository
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
            val dataSource: RadioCloudDataSource = RadioCloudDataSource.Base(service)
            val mapper: RadioStationDomainMapper = RadioStationDomainMapper.Base()
            val repository: RadioRepository = BaseRadioRepository(dataSource, mapper)

            when(val radio = repository.fetchRadioStationById(6)) {
                is Result.Success -> Log.d("App", "Success Result - ${radio.result}")
                is Result.Error -> Log.d("App", "Error Result - ${radio.result}")
            }
        }
    }
}