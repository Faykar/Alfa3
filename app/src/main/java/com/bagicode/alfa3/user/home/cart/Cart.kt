package com.bagicode.alfa3.user.home.cart

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class getCart (
    var key: String ?="",
    var harga: Int ?= 0,
    var jenis: String ?="",
    var desc: String ?="",
    var jumlah: Int ?= 1,
    var url: String ?="",
    var stok: Int ?= 0
) : Parcelable