package com.example.pixel7crashtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.imageLoader
import coil.request.ImageRequest

interface Item

data class ImageItem(
    val url: String,
) : Item

data class WebViewItem(
    val url: String,
) : Item

class MainAdapter(diffCallback: ItemCallback<Item>) : ListAdapter<Item, ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is ImageItem -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            0 -> ImageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.imageview_item, parent, false)
            )

            else -> WebViewViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.webview_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = currentList[position]) {
            is ImageItem -> (holder as ImageViewHolder).bindView(item)
            is WebViewItem -> (holder as WebViewViewHolder).bindView(item)
        }
    }

    class ImageViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bindView(item: ImageItem) {
            val imageRequest = ImageRequest.Builder(itemView.context)
                .data(item.url)
                .target(itemView.findViewById<ImageView>(R.id.imageview))

            itemView.context.imageLoader.enqueue(imageRequest.build())
        }
    }

    class WebViewViewHolder(itemView: View) : ViewHolder(itemView) {

        init {
            itemView.findViewById<WebView>(R.id.webview).run {
                webViewClient = WebViewClient()
                CookieManager.getInstance()
                    .setAcceptCookie(true)
                CookieManager.getInstance()
                    .setAcceptThirdPartyCookies(
                        this,
                        true,
                    )
                settings.run {
                    javaScriptEnabled = true
                    useWideViewPort = true
                    loadWithOverviewMode = true
                    domStorageEnabled = true
                }
            }

        }
        fun bindView(item: WebViewItem) {
            itemView.findViewById<WebView>(R.id.webview).loadUrl(item.url)
        }
    }

}