package br.com.coupledev.launchnews.presentation.binding

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.coupledev.launchnews.R
import br.com.coupledev.launchnews.data.model.Post
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@BindingAdapter("postTitle")
fun TextView.setPostTitle(post: Post?) {
    post?.let {
        text = it.title
    }
}

@BindingAdapter("postSummary")
fun TextView.setPostSummary(post: Post?) {
    post?.let {
        text = post.summary
    }
}

@BindingAdapter("postImage")
fun ImageView.setImage(post: Post?) {
    post?.let {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(this).load(post.imageUrl)
            .placeholder(circularProgressDrawable).into(this)
    }
}

@BindingAdapter("itemHasLaunch")
fun Chip.setHasLaunch(post: Post?) {
    post?.let {
        visibility = if (it.hasLaunch()) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
        val count = post.getLaunchCount()
        this.text = resources.getQuantityString(R.plurals.numberOfLaunchEvents, count, count)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("postPublisherDate")
fun Chip.setUpdate(post: Post?) {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu")
        .withZone(ZoneId.from(ZoneOffset.UTC))
    with(formatter) {
        post?.let {
            val date = Instant.parse(post.publishedAt)
            text = this.format(date)
        }
    }
}