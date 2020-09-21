package com.bagicode.alfa3.user.home.tim.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class addTimKecil(
    var harga: Int = 0,
    var stok: Int = 0,
    var desc: String ?= "",
    var url: String ?=""
)

@Parcelize
data class getTimKecil (
    var key: String ?= "",
    var harga: Int ?= 0,
    var jenis: String ="",
    var desc: String ?= "",
    var url: String ?="",
    var stok: Int ?= 0
): Parcelable
