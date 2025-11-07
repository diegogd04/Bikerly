package edu.iesam.bikerly.domain

import android.net.Uri

data class Motorbike(
    val id: String,
    val make: String,
    val model: String,
    val year: String,
    val type: String,
    val displacement: String,
    val img: Uri?
)