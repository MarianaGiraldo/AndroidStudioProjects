package com.example.new_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.new_app.models.Conference
import com.example.new_app.network.Callback
import com.example.new_app.network.FirestoreService
import java.lang.Exception

class ConferencesViewModel {
    private val firestoreService = FirestoreService()
    val listConferences = MutableLiveData<List<Conference>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        conferencesFromFirebase()
    }

    private fun conferencesFromFirebase(){
        firestoreService.getConferences(object : Callback<List<Conference>>{
            override fun onSuccess(result: List<Conference>?) {
                listConferences.postValue(result)
                Log.d("ConferencesList", "Conferencias añadidas: "+ listConferences.value.toString())
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                processFinished()
            }
        })
    }

    fun processFinished(){
        isLoading.value = true
    }

}