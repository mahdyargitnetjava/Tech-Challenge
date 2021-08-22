package com.example.myapplication.ui.main

import android.content.Context
import android.media.MediaPlayer
import android.os.Vibrator
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.card.MaterialCardView
import com.skydoves.progressview.ProgressView
import com.skydoves.rainbow.Rainbow
import com.skydoves.rainbow.RainbowOrientation
import com.skydoves.rainbow.color
import com.skydoves.whatif.whatIfNotNullOrEmpty
import com.example.myapplication.databinding.PictureItemBinding
import com.example.myapplication.databinding.SoundItemBinding
import com.example.myapplication.databinding.VibratorItemBinding
import timber.log.Timber
import java.lang.Exception
import android.content.res.AssetFileDescriptor
import com.example.myapplication.data.model.response.CardResponse


object MainViewBinding {

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, text: String?) {
        text.whatIfNotNullOrEmpty {
            Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
        }
    }

    @JvmStatic
    @BindingAdapter("titleCard")
    fun bindTitle(view: AppCompatEditText, title: String?){
        title.whatIfNotNullOrEmpty {
            view.setText(title)
        }
    }
    @JvmStatic
    @BindingAdapter("descriptionCard")
    fun bindDescription(view: AppCompatEditText, description: String?){
        description.whatIfNotNullOrEmpty {
            view.setText(description)
        }
    }

    @JvmStatic
    @BindingAdapter("bindCard")
    fun bindCard(view :FrameLayout, card: CardResponse.CardInfo?){
        card?.run {
            when(card.code){
                0 -> {
                    val binding = PictureItemBinding.bind(view)
                    binding.model = card
                }
                1 -> {
                    val binding = SoundItemBinding.bind(view)
                    binding.model = card
                    try {
                        MediaPlayer().run {
                            val afd: AssetFileDescriptor = App.appContext.assets.openFd("sound.mp3")
                            setDataSource(afd.fileDescriptor,afd.startOffset,afd.length)
                            prepare()
                            start()
                        }
                    }catch (e: Exception){
                        Timber.e(e.message)
                    }
                }
                2 -> {
                    val binding = VibratorItemBinding.bind(view)
                    binding.model = card
                    try {
                        (App.appContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(1000)
                    }catch (e: Exception){
                        Timber.e(e.message)
                    }
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("paletteImage", "paletteCard")
    fun bindLoadImagePalette(view: AppCompatImageView, url: String, paletteCard: MaterialCardView) {
        Glide.with(view.context)
            .load(url)
            .listener(
                GlidePalette.with(url)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val rgb = palette?.dominantSwatch?.rgb
                        if (rgb != null) {
                            paletteCard.setCardBackgroundColor(rgb)
                        }
                    }.crossfade(true)
            ).into(view)
    }

    @JvmStatic
    @BindingAdapter("paletteImage", "paletteView")
    fun bindLoadImagePaletteView(view: AppCompatImageView, url: String, paletteView: View) {
        val context = view.context
        Glide.with(context)
            .load(url)
            .listener(
                GlidePalette.with(url)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val light = palette?.lightVibrantSwatch?.rgb
                        val domain = palette?.dominantSwatch?.rgb
                        if (domain != null) {
                            if (light != null) {
                                Rainbow(paletteView).palette {
                                    +color(domain)
                                    +color(light)
                                }.background(orientation = RainbowOrientation.TOP_BOTTOM)
                            } else {
                                paletteView.setBackgroundColor(domain)
                            }
                            if (context is AppCompatActivity) {
                                context.window.apply {
                                    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                                    statusBarColor = domain
                                }
                            }
                        }
                    }.crossfade(true)
            ).into(view)
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if (shouldBeGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("onBackPressed")
    fun bindOnSelectClick(view: View, onBackPress: Boolean) {
        val context = view.context
        if (onBackPress && context is OnBackPressedDispatcherOwner) {
            view.setOnClickListener {
                context.onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("progressView_labelText")
    fun bindProgressViewLabelText(progressView: ProgressView, text: String?) {
        progressView.labelText = text
    }

    @JvmStatic
    @BindingAdapter("progressView_progress")
    fun bindProgressViewProgress(progressView: ProgressView, value: Int?) {
        if (value != null) {
            progressView.progress = value.toFloat()
        }
    }

    @JvmStatic
    @BindingAdapter("progressView_max")
    fun bindProgressViewMax(progressView: ProgressView, value: Int?) {
        if (value != null) {
            progressView.max = value.toFloat()
        }
    }
}
