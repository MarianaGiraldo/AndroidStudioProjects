package com.example.new_app.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.new_app.models.Comment
import com.example.new_app.models.Conference
import com.example.new_app.models.Speaker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.util.*


class FirestoreService {
    private var firebaseFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    val listComments = MutableLiveData<List<Comment>>()

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
                for (doc in result) {
                    val list = result.toObjects(Speaker::class.java)
                    callback.onSuccess(list)
                    break
                }
            }
            .addOnFailureListener { exception ->
                Log.w("GetSpeakers", "Error getting documents: ", exception)
            }
    }

    fun getComments(callback: Callback<List<Comment>>){
        Log.i("FirestoreService","Inside getComments")
        firebaseFirestore.collection("comments")
            .orderBy("name")
            .get()
            .addOnSuccessListener {
                    result ->
                for (doc in result){
                    val list = result.toObjects(Comment::class.java)
                    callback.onSuccess(list)
                    Log.i("GetComments","Success on getComments Firestore Service")
                    break
                }
            }
            .addOnFailureListener { exception ->
                Log.w("GetComments", "Error getting documents: ", exception)
            }
    }

}