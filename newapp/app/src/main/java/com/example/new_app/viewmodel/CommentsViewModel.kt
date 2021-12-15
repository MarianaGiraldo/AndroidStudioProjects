package com.example.new_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.new_app.models.Comment
import com.example.new_app.models.Conference
import com.example.new_app.network.Callback
import com.example.new_app.network.FirestoreService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.lang.Exception
import java.util.ArrayList

class CommentsViewModel {
    private var firebaseFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    private var _listComments = MutableLiveData<List<Comment>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        firebaseFirestore.firestoreSettings = settings
        //commentsFromFirebase()
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
                        Log.d("CommentsList", "Comment added: "+ _listComments.value.toString())
                        processFinished()
                    }
                    _listComments.value = allComments
                }
            }
        }
    }

    /*
    private fun commentsFromFirebase(): MutableLiveData<List<Comment>> {
        firestoreService.getComments(object : Callback<List<Comment>>{
            override fun onSuccess(result: List<Comment>?) {
                _listComments.postValue(result)
                Log.d("CommentsList", "Comments añadidos: "+ _listComments.value.toString())
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                Log.d("CommentsList", "Comments no obtenidos: $exception")
                processFinished()
            }
        })
        return _listComments
    }

     */

    private fun processFinished(){
        isLoading.value = true
    }

    internal var listComments: MutableLiveData<List<Comment>>
        get() {return _listComments}
        set(value) {_listComments = value}

}