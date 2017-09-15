package com.ripzery.tamboon.data

import com.google.gson.annotations.SerializedName

/**
 * Created by ripzery on 9/15/17.
 */
object Tamboon {
    data class Charity(val id: Int, val name: String, @SerializedName("logo_url") val logo: String)
}