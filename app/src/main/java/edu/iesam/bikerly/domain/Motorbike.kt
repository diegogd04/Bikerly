package edu.iesam.bikerly.domain

import android.net.Uri

data class Motorbike(
    val id: Int,
    val make: String,
    val model: String,
    val year: Int,
    val type: String,
    val displacement: Int,
    val img: Uri?
)