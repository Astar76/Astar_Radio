package com.astar.astarradio.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astar.astarradio.core.Result
import com.astar.astarradio.domain.RadioRepository
import com.astar.astarradio.domain.RadioStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: RadioRepository) : ViewModel() {

    private var _users = MutableLiveData<Result<List<RadioStation>, Throwable>>()
    val users: LiveData<Result<List<RadioStation>, Throwable>> = _users

    init {
        viewModelScope.launch {
            _users.postValue(repository.fetchRadioStations())
        }
    }
}