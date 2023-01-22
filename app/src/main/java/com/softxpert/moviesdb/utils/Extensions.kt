package com.softxpert.moviesdb.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.CheckResult
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.softxpert.moviesdb.R
import com.softxpert.moviesdb.ui.main.SliderAdapter
import com.softxpert.moviesdb.ui.model.MovieDetailsUI
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

inline fun CoroutineScope.createExceptionHandler(
    message: String,
    crossinline action: (throwable: Throwable) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
    Logger.e(throwable, message)
    throwable.printStackTrace()

    launch {
        action(throwable)
    }
}

@BindingAdapter("app:setupImage")
fun bindImageView(imageView: ImageView, image: String?) {
    Glide.with(imageView.context)
        .load(image?.ifEmpty { null })
        .error(R.drawable.placeholder)
        .placeholder(R.drawable.placeholder)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

@BindingAdapter(value = ["app:bindimage1", "app:bindimage2"], requireAll = false)
fun SliderView.bindSlider(image1: String?, image2: String?) {
    val list: ArrayList<String> = ArrayList()
    image1?.let { list.add(it) }
    image2?.let { list.add(it) }
    val sliderAdapter = SliderAdapter(list)
    setSliderAdapter(sliderAdapter)
    setIndicatorAnimation(IndicatorAnimationType.WORM)
    setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
    scrollTimeInSec = 3
    startAutoCycle()

}

@BindingAdapter("app:setMovieDetails")
fun bindMovieDetails(textView: TextView, movieDetailsUI: MovieDetailsUI?) {
    movieDetailsUI?.let { details ->
        var text = details.releaseDate + " • "
        text += details.voteAverage.toString() + " (${details.voteCount})"
        if (details.adult)
            text += " • Adult"
        textView.text = text
    }
}

@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}
