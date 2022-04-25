package com.astar.astarradio.presentation.radiolist

import com.astar.astarradio.presentation.RadioUi

sealed class RadioListResult {

    object Loading : RadioListResult()
    object EmptyResult : RadioListResult()
    data class SuccessResult(val result: List<RadioUi>) : RadioListResult()
    data class ErrorResult(val error: Throwable) : RadioListResult()
}

