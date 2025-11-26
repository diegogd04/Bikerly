package edu.iesam.bikerly.app.presentation

import android.net.Uri
import android.widget.ImageView
import coil3.load

fun ImageView.loadUrl(url: Uri?) {
    this.load(url)
}