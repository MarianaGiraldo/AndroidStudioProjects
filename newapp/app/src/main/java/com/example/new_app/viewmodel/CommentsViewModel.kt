package com.example.new_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.new_app.models.Comment
import com.example.new_app.models.Conference
import com.example.new_app.network.Callback
import com.example.new_app.network.FirestoreService
import java.lang.Exception

class CommentsViewModel {
    private val firestoreService = FirestoreService()
    val listComments = MutableLiveData<List<Comment>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        commentsFromFirebase()
    }

    private fun commentsFromFirebase(){
        firestoreService.getComments(object : Callback<List<Comment>>{
            override fun onSuccess(result: List<Comment>?) {
                listComments.postValue(result)
                Log.d("CommentsList", "Comments añadidos: "+ listComments.value.toString())
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                Log.d("CommentsList", "Comments no obtenidos: $exception")
                processFinished()
            }
        })
    }

    fun processFinished(){
        isLoading.value = true
    }

}