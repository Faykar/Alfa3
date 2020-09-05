package com.bagicode.alfa3.user.home.pudding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class addPudding(
    var desc: String ="",
    var url: String ="",
    var jenis: String ?="",
    var stok: Int =0,
    var harga: Int =0
)


@Parcelize
data class getPudding (
    var key: String ?="",
    var desc: String ?="",
    var url: String ?="",
    var jenis: String?="",
    var stok: Int ?= 0,
    var harga: Int ?= 0
): Parcelable
