package com.telstra.testapp.service

import com.google.gson.annotations.SerializedName
import com.telstra.testapp.data.FactDetail

data class FactsResponse(
    @SerializedName("title")
    val title: String?,
    @SerializedName("rows")
    val items: List<FactDetail>?
)