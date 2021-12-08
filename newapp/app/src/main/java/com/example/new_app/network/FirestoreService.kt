package com.example.new_app.network

import com.example.new_app.models.Conference
import com.example.new_app.models.Speaker
import com.google.firebase.firestore.FirebaseFirestore //conection
import com.google.firebase.firestore.FirebaseFirestoreSettings //config
import com.google.firebase.firestore.ktx.toObjects


class FirestoreService {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()

    init{
        firebaseFirestore.firestoreSettings = settings
    }

    fun getConferences(callback: Callback<List<Conference>>){
        firebaseFirestore.collection("conferences")
            .orderBy("title")
            .get()
            .addOnSuccessListener {
                result ->
                for (doc in result){
                    val list = result.toObjects(Conference::class.java)
                    callback.onSuccess(list)
                    break
                }
            }
    }

    fun getSpeakers(callback: Callback<List<Speaker>>){
        firebaseFirestore.collection("speakers")
            .orderBy("name")
            .get()
            .addOnSuccessListener {
                    result ->
                for (doc in result){
                    val list = result.toObjects(Speaker::class.java)
                    callback.onSuccess(list)
                    break
                }
            }
    }

}