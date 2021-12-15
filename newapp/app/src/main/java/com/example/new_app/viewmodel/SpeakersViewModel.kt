package com.example.new_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.new_app.models.Conference
import com.example.new_app.models.Speaker
import com.example.new_app.network.Callback
import com.example.new_app.network.FirestoreService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.lang.Exception
import java.util.ArrayList

class SpeakersViewModel {
    private var firebaseFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    //private val firestoreService = FirestoreService()
    private var _listSpeakers = MutableLiveData<List<Speaker>>()
    private val isLoading = MutableLiveData<Boolean>()
    private val TAG = "SpeakersListener"

    fun refresh(){
        //speakersFromFirebase()
        listentoSpeakers()
    }

    /**
     * This will hear updates from Firebase
     */
    private fun listentoSpeakers() {
        Log.i(TAG, "Inside listen to Speakers")
        firebaseFirestore.collection("speakers").addSnapshotListener{
                snapshot, e ->
            run {
                Log.d(TAG, "ListenToSpeakers is running")
                if (e != null) {
                    Log.w(TAG, "Listen Failed", e)
                    return@addSnapshotListener
                }
                Log.d(TAG, "Error is null. Listener running")
                if (snapshot != null) {
                    val allSpeakers = ArrayList<Speaker>()
                    val documents = snapshot.documents
                    documents.forEach {
                        val speaker = it.toObject(Speaker::class.java)
                        if (speaker != null){
                            allSpeakers.add(speaker)
                        }
                        Log.d("SpeakersList", "Conference added: "+ _listSpeakers.value.toString())
                        processFinished()
                    }
                    _listSpeakers.value = allSpeakers
                    Log.d(TAG, "ListSpeakers size is: " + (_listSpeakers.value as ArrayList<Speaker>).size)
                }
                else{
                    Log.w(TAG, "Snapshot is null", e)
                }
            }
        }
    }

    /*
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

     */

    private fun processFinished(){
        isLoading.value = true
    }

    internal var listSpeakers: MutableLiveData<List<Speaker>>
        get() {return _listSpeakers}
        set(value) {_listSpeakers = value}

}