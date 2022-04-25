package com.astar.astarradio.presentation.radiolist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astar.astarradio.core.Result
import com.astar.astarradio.domain.RadioRepository
import com.astar.astarradio.domain.RadioDomain
import com.astar.astarradio.player.PlaybackState
import com.astar.astarradio.player.PlayableRadio
import com.astar.astarradio.presentation.RadioUi
import com.astar.astarradio.presentation.RadioUiMapper
import com.astar.astarradio.presentation.radiolist.RadioListResult.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RadioListViewModel @Inject constructor(
    private val repository: RadioRepository,
    private val mapper: RadioUiMapper,
    // private val playbackState: PlaybackState
) : ViewModel(), RadioActionCallback, PlaybackState.Callback {

    private val playbackState = PlaybackState.newInstance()

    private var _radioStations = MutableLiveData<RadioListResult>()
    val radioStations: LiveData<RadioListResult> = _radioStations

    init {
        viewModelScope.launch {
            loadRadioStations()
        }
        playbackState.addCallback(this)
    }

    private suspend fun loadRadioStations() {
        _radioStations.postValue(Loading)
        _radioStations.postValue(preparedResult(repository.fetchRadioStations()))
    }

    private fun preparedResult(data: Result<List<RadioDomain>, Throwable>): RadioListResult {
        return when (data) {
            is Result.Success -> if (data.result.isEmpty())
                EmptyResult
            else
                SuccessResult(data.result.map { mapper.map(it) })
            is Result.Error -> ErrorResult(data.result)
        }
    }

    override fun onClickItem(station: RadioUi) {
        Log.d("RadioListViewModel", "onClickItem() $station")
        val playableRadio = PlayableRadio(station.name, station.streamingUrl)
        playbackState.playRadio(playableRadio)
        playbackState.setPlaying(true)
    }

    override fun onClickFavorite(station: RadioUi) {
        Log.d("RadioListViewModel", "onClickItem() $station")
        viewModelScope.launch {
            repository.addToFavorite(mapper.map(station))
            loadRadioStations()
        }
    }


    /** region [PlaybackState.Callback] */

    override fun onRadioUpdate(radio: PlayableRadio?) {

    }

    override fun onPlayingUpdate(isPlaying: Boolean) {

    }

    /** endregion [PlaybackState.Callback] */

    override fun onCleared() {
        playbackState.removeCallback(this)
        super.onCleared()
    }
}

