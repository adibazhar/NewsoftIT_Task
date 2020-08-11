package com.example.newsoftittask.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post (
    var id:Int = -1,

    var userId:Int = -1,
    var title:String = "",
    @SerializedName("body")
    var message:String = ""

) : Parcelable