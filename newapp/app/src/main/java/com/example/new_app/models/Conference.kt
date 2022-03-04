package com.example.new_app.models

import java.io.Serializable
import java.util.*

class Conference : Serializable {
    var title: String = String()
    var description: String = String()
    var tag: String=  String()
    var datetime: Date = Date()
    var speaker: String = String()
    lateinit var location: Location
}