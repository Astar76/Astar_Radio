package com.astar.astarradio.core

sealed class Result<out R, out E> {

    data class Success<out R>(val result: R) : Result<R, Nothing>()

    data class Error<out E>(val result: E) : Result<Nothing, E>()
}