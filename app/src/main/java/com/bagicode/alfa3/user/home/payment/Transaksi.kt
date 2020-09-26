package com.bagicode.alfa3.user.home.payment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Transaksi (
    var key: String? ="",
    var nama: String? = "",
    var nomor: String? = "",
    var hargaTotal: Int? = 0,
    var bukti: String? =""
) : Parcelable