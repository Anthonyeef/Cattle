package io.github.anthonyeef.cattle.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import io.github.anthonyeef.cattle.GlideApp
import io.github.anthonyeef.cattle.R
import org.jetbrains.anko.dip
import kotlin.math.absoluteValue


class PhotoDisplayActivity : BaseActivity() {

  companion object {
    @JvmField val KEY_IMAGE_URL = "key_image_url"
    @JvmField val KEY_IS_GIF = "key_is_gif"
  }


  private val imageUrl by lazy { intent.extras.getString(KEY_IMAGE_URL) }
  private val isGif by lazy { intent.extras.getBoolean(KEY_IS_GIF, false) }

  @SuppressLint("ClickableViewAccessibility")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_photo_display)

    val imageView = findViewById<ImageView>(R.id.image)

    if (isGif) {
      GlideApp.with(this)
          .load(imageUrl)
          .into(imageView)
    } else {
      GlideApp.with(this)
          .load(imageUrl)
          .dontAnimate()
          .into(imageView)
    }

    var lastY = 0f

    imageView.setOnTouchListener { _, event ->
      when(event.actionMasked) {
        MotionEvent.ACTION_MOVE -> {
          val deltaY = event.y - lastY
          if (deltaY.absoluteValue > dip(15)) {
            this@PhotoDisplayActivity.finish()
          }
        }
      }

      lastY = event.y
      true
    }
  }
}