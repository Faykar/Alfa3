package com.bagicode.alfa3.Admin.sign

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Admin (
    var password: String ?="",
    var username: String ?=""
): Parcelable