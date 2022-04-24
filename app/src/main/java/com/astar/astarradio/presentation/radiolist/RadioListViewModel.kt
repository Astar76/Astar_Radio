package com.astar.astarradio.presentation.radiolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astar.astarradio.core.Result
import com.astar.astarradio.domain.RadioRepository
import com.astar.astarradio.domain.RadioStation
import com.astar.astarradio.presentation.radiolist.RadioListResult.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class RadioListViewModel @Inject constructor(
    private val repository: RadioRepository,
) : ViewModel() {

    private var _radioStations = MutableLiveData<RadioListResult>()
    val radioStations: LiveData<RadioListResult> = _radioStations

    init {
        viewModelScope.launch {
            _radioStations.postValue(Loading)
            _radioStations.postValue(mapToRadioListResult(repository.fetchRadioStations()))
        }
    }

    private fun mapToRadioListResult(data: Result<List<RadioStation>, Throwable>): RadioListResult {
        return when (data) {
            is Result.Success -> if (data.result.isEmpty()) EmptyResult else SuccessResult(data.result)
            is Result.Error -> ErrorResult(data.result)
        }
    }
}

