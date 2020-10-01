package com.bagicode.alfa3.user.home.payment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class isiTransaksi (
    var key: String ?="",
    var harga: Int ?= 0,
    var jenis: String ?="",
    var desc: String ?="",
    var url: String ?="",
    var jumlah: Int ?= 0
) : Parcelable