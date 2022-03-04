package com.example.new_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.new_app.models.Speaker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.util.*

class SpeakersViewModel {
    private var firebaseFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    private var _listSpeakers = MutableLiveData<List<Speaker>>()
    val isLoading = MutableLiveData<Boolean>()
    private val TAG = "SpeakersListener"

    fun refresh(){
        firebaseFirestore.firestoreSettings = settings
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

    private fun processFinished(){
        isLoading.value = true
    }

    internal var listSpeakers: MutableLiveData<List<Speaker>>
        get() {return _listSpeakers}
        set(value) {_listSpeakers = value}

}