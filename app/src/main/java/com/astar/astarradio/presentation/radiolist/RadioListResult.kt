package com.astar.astarradio.presentation.radiolist

import com.astar.astarradio.domain.RadioStation

sealed class RadioListResult {

    object Loading : RadioListResult()
    object EmptyResult : RadioListResult()
    data class SuccessResult(val result: List<RadioStation>) : RadioListResult()
    data class ErrorResult(val error: Throwable) : RadioListResult()
}

