package com.example.new_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.new_app.models.Comment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class CommentsViewModel {
    private var firebaseFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    private var _listComments = MutableLiveData<List<Comment>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        firebaseFirestore.firestoreSettings = settings
        listentoComments()
    }

    /**
     * This will hear updates from Firebase
     */
    private fun listentoComments(){
        Log.i("CommentsViewModel", "Inside listen to Comments")
        firebaseFirestore.collection("comments").addSnapshotListener{
                snapshot, e ->
            run {
                if (e != null) {
                    Log.w("FireStoreServiceListen", "Listen Failed", e)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val allComments = ArrayList<Comment>()
                    val documents = snapshot.documents
                    documents.forEach {
                        val comment = it.toObject(Comment::class.java)
                        if (comment != null){
                            allComments.add(comment)
                        }
                        processFinished()
                    }
                    _listComments.value = allComments
                }
            }
        }
    }

    private fun processFinished(){
        isLoading.value = true
    }

    internal var listComments: MutableLiveData<List<Comment>>
        get() {return _listComments}
        set(value) {_listComments = value}

}