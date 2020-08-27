package com.bagicode.alfa3.log.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User (
    var nomor: String?="",
    var nama: String ?="",
    var password: String ?="",
    var username: String ?="",
    var url: String ?=""

    ): Parcelable