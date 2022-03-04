package com.example.new_app.adapter

import com.example.new_app.models.Speaker

interface SpeakerListener {
    fun onSpeakerClicked(speaker: Speaker, position: Int)
}