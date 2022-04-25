package com.astar.astarradio.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astar.astarradio.core.Result
import com.astar.astarradio.domain.RadioDomain
import com.astar.astarradio.domain.RadioRepository
import com.astar.astarradio.presentation.RadioUi
import com.astar.astarradio.presentation.RadioUiMapper
import com.astar.astarradio.presentation.radiolist.RadioActionCallback
import com.astar.astarradio.presentation.radiolist.RadioListResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: RadioRepository,
    private val mapper: RadioUiMapper
): ViewModel(), RadioActionCallback {

    private val _radioStations = MutableLiveData<RadioListResult>()
    val radioListResult: LiveData<RadioListResult> = _radioStations

    init {
        viewModelScope.launch {
            loadRadioStations()
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            loadRadioStations()
        }
    }

    private suspend fun loadRadioStations() {
        _radioStations.postValue(RadioListResult.Loading)
        _radioStations.postValue(preparedResult(repository.fetchFavoriteRadioStations()))
    }

    private fun preparedResult(data: Result<List<RadioDomain>, Throwable>): RadioListResult {
        return when (data) {
            is Result.Success -> if (data.result.isEmpty())
                RadioListResult.EmptyResult
            else
                RadioListResult.SuccessResult(data.result.map { mapper.map(it) })
            is Result.Error -> RadioListResult.ErrorResult(data.result)
        }
    }

    override fun onClickItem(station: RadioUi) {

    }

    override fun onClickFavorite(station: RadioUi) {

    }
}