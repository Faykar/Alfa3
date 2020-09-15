package com.bagicode.alfa3.user.log.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var username: String ?="",
    var nama: String ?="",
    var nomor: String ?= "",
    var password: String ?="",
    var url: String ?=""

    ): Parcelable