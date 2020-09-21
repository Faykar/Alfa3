package com.bagicode.alfa3.user.home.bubur.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class addBuburKecil(
    var harga: Number ?= 0,
    var stok: Number ?= 0,
    var desc: String ?="",
    var url: String ?=""
)

@Parcelize
data class getBuburKecil(
    var key: String ?="",
    var harga: Int ?= 0,
    var jenis: String ?= "",
    var desc: String ?="",
    var url: String ?="",
    var stok: Int ?= 0
):Parcelable