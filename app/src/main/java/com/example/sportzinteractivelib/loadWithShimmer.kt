package com.example.sportzinteractivelib

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
import com.example.interview.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

fun ImageView.loadWithShimmer(
    url: String?,
    @DrawableRes errorDrawable: Int,
    builder: ImageRequest.Builder.() -> Unit = {
        val shimmer =
            Shimmer.ColorHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.7f) //the alpha of the underlying children
                .setHighlightAlpha(0.6f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setBaseColor(ContextCompat.getColor(context, R.color.white)).setAutoStart(true)
                .build()
        // This is the placeholder for the imageView
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
        placeholder(shimmerDrawable)
        crossfade(true)
        error(errorDrawable)
    },
): Disposable {
    return load(url, builder = builder)
}