package com.example.new_app.models

import android.os.Parcel
import android.os.Parcelable

class Speaker() : Parcelable {
    var name = ""
    var jobtitle = ""
    var workplace = ""
    var biography = ""
    var twitter = ""
    var image = ""
    var category = 0

    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        jobtitle = parcel.readString().toString()
        workplace = parcel.readString().toString()
        biography = parcel.readString().toString()
        twitter = parcel.readString().toString()
        image = parcel.readString().toString()
        category = parcel.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(jobtitle)
        parcel.writeString(workplace)
        parcel.writeString(biography)
        parcel.writeString(twitter)
        parcel.writeString(image)
        parcel.writeInt(category)
    }

    companion object CREATOR : Parcelable.Creator<Speaker> {
        override fun createFromParcel(parcel: Parcel): Speaker {
            return Speaker(parcel)
        }

        override fun newArray(size: Int): Array<Speaker?> {
            return arrayOfNulls(size)
        }
    }

}