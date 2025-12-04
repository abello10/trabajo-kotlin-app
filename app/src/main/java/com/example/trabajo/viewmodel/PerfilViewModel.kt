package com.example.trabajo.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PerfilViewModel : ViewModel() {
    private val _foto = MutableStateFlow<Uri?>(null)
    val foto: StateFlow<Uri?> = _foto

    fun setDesdeGaleria(uri: Uri?) { _foto.value = uri }
    fun setDesdeCamara(uri: Uri?)  { _foto.value = uri }
}