package com.example.new_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.new_app.models.Conference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class ConferencesViewModel {
    private var firebaseFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    private var _listConferences = MutableLiveData<List<Conference>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        firebaseFirestore.firestoreSettings = settings
        listentoConferences()
    }

    /**
     * This will hear updates from Firebase
     */
    private fun listentoConferences(){
        Log.i("ConferencesViewModel", "Inside listen to Conferences")
        firebaseFirestore.collection("conferences").addSnapshotListener{
                snapshot, e ->
            run {
                Log.d("ConferencesListener", "ListenToConferences is running")
                if (e != null) {
                    Log.w("FireStoreServiceListen", "Listen Failed", e)
                    return@addSnapshotListener
                }
                Log.d("ConferencesListener", "Error is null. Listener running")
                if (snapshot != null) {
                    val allConferences = ArrayList<Conference>()
                    val documents = snapshot.documents
                    documents.forEach {
                        val conference = it.toObject(Conference::class.java)
                        if (conference != null){
                            allConferences.add(conference)
                        }
                        Log.d("ConferencesList", "Conference added: "+ _listConferences.value.toString())
                        processFinished()
                    }
                    _listConferences.value = allConferences
                    Log.d("ConferencesListener", "ListConferences size is: " + (_listConferences.value as ArrayList<Conference>).size)
                }
                else{
                    Log.w("FireStoreServiceListen", "Snapshot is null", e)
                }
            }
        }
    }

    private fun processFinished(){
        isLoading.value = true
    }

    internal var listConferences: MutableLiveData<List<Conference>>
        get() {return _listConferences}
        set(value) {_listConferences = value}

}