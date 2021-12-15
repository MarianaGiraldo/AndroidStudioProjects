package com.example.new_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.new_app.models.Conference
import com.example.new_app.network.Callback
import com.example.new_app.network.FirestoreService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.lang.Exception
import java.util.ArrayList

class ConferencesViewModel {
    private var firebaseFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    //private val firestoreService = FirestoreService()
    private var _listConferences = MutableLiveData<List<Conference>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        firebaseFirestore.firestoreSettings = settings
        //conferencesFromFirebase()
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
    /*
    private fun conferencesFromFirebase(){
        firestoreService.getConferences(object : Callback<List<Conference>>{
            override fun onSuccess(result: List<Conference>?) {
                _listConferences.postValue(result)
                Log.d("ConferencesList", "Conferencias añadidas: "+ _listConferences.value.toString())
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

    internal var listConferences: MutableLiveData<List<Conference>>
        get() {return _listConferences}
        set(value) {_listConferences = value}

}