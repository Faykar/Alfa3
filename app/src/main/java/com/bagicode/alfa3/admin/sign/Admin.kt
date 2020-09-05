package com.bagicode.alfa3.admin.sign

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Admin (
    var password: String ?="",
    var username: String ?=""
): Parcelable