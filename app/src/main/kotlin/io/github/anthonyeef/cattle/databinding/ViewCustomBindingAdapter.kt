package io.github.anthonyeef.cattle.databinding

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.extension.goneIf
import io.github.anthonyeef.cattle.utils.StatusParsingUtils

// bind status source to TextView
@BindingAdapter("statusSource")
fun bindStatusSource(view: TextView, source: String) {
    StatusParsingUtils.setSource(view, source)
}

// bind parsed status content to TextView
@BindingAdapter("status")
fun bindStatusContent(view: TextView, status: Status) {
    if (status.text.isNotEmpty()) {
        StatusParsingUtils.setStatus(view, status.text)
    } else if (status.photo != null) {
        view.text = app.resources.getString(R.string.text_photo_only_status)
    }
}

// bind large size status photo to ImageView
@BindingAdapter("status")
fun bindStatusPhoto(view: ImageView, status: Status) {
    view.goneIf(status.photo == null)
    if (status.photo != null) {
        Glide.with(view.context)
                .load(status.photo?.largeurl)
                .into(view)
    }
}

// bind normal size photo to ImageView
@BindingAdapter("previewPhoto")
fun bindStatusPreviewPhoto(view: ImageView, status: Status) {
    view.goneIf(status.photo == null)
    if (status.photo != null) {
        Glide.with(view.context)
                .load(status.photo?.imageurl)
                .into(view)
    }
}
