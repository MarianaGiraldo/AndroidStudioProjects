package com.example.new_app.adapter

import com.example.new_app.models.Conference

interface ConferenceListener {
    fun onConferenceClicked(conference: Conference, position: Int)
}