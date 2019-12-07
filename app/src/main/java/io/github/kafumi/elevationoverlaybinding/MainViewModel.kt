package io.github.kafumi.elevationoverlaybinding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val elevationMax = application.resources.getDimension(R.dimen.elevation_max)
    val elevationProgress1 = MutableLiveData(0)
    val elevationProgress2 = MutableLiveData(0)
    val elevation1 = elevationProgress1.map { it * elevationMax / 100 }
    val elevation2 = elevationProgress2.map { it * elevationMax / 100 }
}