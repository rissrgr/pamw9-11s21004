package com.ifs21004.whatsapp

import android.graphics.drawable.Icon
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class Email (
    var name: String,
    var icon: Int,
    var subject: String,
    var text: String,
) : Parcelable