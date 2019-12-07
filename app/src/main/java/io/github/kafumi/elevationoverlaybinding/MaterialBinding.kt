package io.github.kafumi.elevationoverlaybinding

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.MaterialShapeUtils

@BindingAdapter("materialElevation")
fun View.setElevationWithMaterialOverlay(elevation: Float) {
    this.elevation = elevation

    when (val originalBackground = background) {
        is ColorDrawable -> {
            val drawable = MaterialShapeDrawable()
            drawable.fillColor = ColorStateList.valueOf(originalBackground.color)
            drawable.initializeElevationOverlay(context)
            drawable.elevation = elevation
            background = drawable

            doOnAttachedToWindow {
                MaterialShapeUtils.setParentAbsoluteElevation(this)
            }
        }
        is MaterialShapeDrawable -> {
            originalBackground.elevation = elevation
        }
        else -> {
            return
        }
    }
}

private fun View.doOnAttachedToWindow(action: View.() -> Unit) {
    if (isAttachedToWindow) {
        action()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                removeOnAttachStateChangeListener(this)
                action()
            }

            override fun onViewDetachedFromWindow(v: View) {
            }
        })
    }
}

private val View.parentAbsoluteElevation: Float
    get() {
        var absoluteElevation = 0f
        var viewParent = parent
        while (viewParent is View) {
            absoluteElevation += ViewCompat.getElevation(viewParent)
            viewParent = viewParent.getParent()
        }
        return absoluteElevation
    }

