package com.example.new_app.network

import java.lang.Exception

interface Callback<T> {
    //Función cuando se cumple
    fun onSuccess(result: T?)

    //función cuando no se cumple
    fun onFailed(exception: Exception)

}