package com.example.leafcare.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentViewModel : ViewModel() {

    private val _currentPictureBitmap = MutableLiveData<Bitmap>()
    private val _currentPictureUri = MutableLiveData<Uri>()
    private val _currentPictureSelect = MutableLiveData<String>()

    val currentPictureBitmap: LiveData<Bitmap> get() = _currentPictureBitmap
    val currentPictureUri: LiveData<Uri> get() = _currentPictureUri
    val currentPictureSelect: LiveData<String> get() = _currentPictureSelect

    fun currentPictureEditBitmap(bitMapPicture: Bitmap) {
        _currentPictureBitmap.value = bitMapPicture
        _currentPictureSelect.value = "bit"
    }
    fun currentPictureEditUri(uriPicture: Uri) {
        _currentPictureUri.value = uriPicture
        _currentPictureSelect.value = "uri"

    }


}