package com.example.new_app.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.new_app.models.Speaker
import com.example.new_app.network.Callback
import com.example.new_app.network.FirestoreService
import java.lang.Exception

class SpeakersViewModel {
    private val firestoreService = FirestoreService()
    val listSpeakers = MutableLiveData<List<Speaker>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        speakersFromFirebase()
    }

    private fun speakersFromFirebase(){
        firestoreService.getSpeakers(object : Callback<List<Speaker>> {
            override fun onSuccess(result: List<Speaker>?) {
                listSpeakers.postValue(result)
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