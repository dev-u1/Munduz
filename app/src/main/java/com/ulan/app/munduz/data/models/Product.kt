package com.ulan.app.munduz.developer

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ulan.app.munduz.data.models.Picture
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var id: String = "",
    var category: String = "",
    var name: String = "",
    var desc: String = "",
    var cost: Int = -1,
    var isVisible: Boolean = false,
    var priceFor: String = "",
    var picture: Picture = Picture("url"),
    var date: Long = 0L
) : Parcelable


