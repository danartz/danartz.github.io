package com.example.trash_it

import android.content.Context
import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrashData (var weight: Int, var height: Int, var dateTime: String,
                var alphaNum: String, var lastEmptied: String, var name: String,
                      var maxHeight: Int, var maxWeight: Int): Parcelable {
    constructor() : this(0, 0, "", "", "", "",
        0, 0)
}


