package com.example.pixel7crashtest

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixel7crashtest.ui.theme.Pixel7CrashtestTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pixel7CrashtestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

private fun getRandomImageUrl(width: Int = 400, height: Int = 400): String {
    return "https://source.unsplash.com/random/${width}x${height}?rid=${Random.nextInt(0, 100)}"
}

@Composable
fun Content() {
    AndroidView(factory = { context ->
        RecyclerView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            layoutManager = LinearLayoutManager(context)
            adapter = MainAdapter(object : ItemCallback<Item>() {
                override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                    return oldItem == newItem
                }
            }).apply {
                submitList(
                    (0..1000).map { it ->
                        if (it % 4 == 0) {
                            WebViewItem("https://www.google.de")
                        } else {
                            ImageItem(getRandomImageUrl())
                        }
                    }
                )
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Pixel7CrashtestTheme {
        Content()
    }
}