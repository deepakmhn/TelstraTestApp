package com.telstra.testapp.data

import com.google.gson.annotations.SerializedName

data class FactDetail(
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("imageHref")
    val imageHref: String?
)