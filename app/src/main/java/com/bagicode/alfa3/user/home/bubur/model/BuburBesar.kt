package com.bagicode.alfa3.user.home.bubur.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class addBuburBesar(
    var harga: String ?= "",
    var stok: String ?= "",
    var desc: String ?= "",
    var url: String ?=""
):Parcelable


@Parcelize
data class getBuburBesar(
    var key: String ?="",
    var harga: Int ?= 0,
    var jenis: String ?="",
    var desc: String ?="",
    var url: String ?="",
    var stok: Int ?= 0
):Parcelable