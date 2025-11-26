package edu.iesam.bikerly.app.domain

sealed class ErrorApp : Throwable() {

    object InternetError : ErrorApp()
    object DataError : ErrorApp()
    object UnknownError : ErrorApp()
}