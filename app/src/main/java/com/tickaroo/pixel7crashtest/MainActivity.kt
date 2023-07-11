package com.tickaroo.pixel7crashtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.tickaroo.pixel7crashtest.ui.theme.Pixel7CrashtestTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Pixel7CrashtestTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Content()
        }
      }
    }
  }
}

private fun getRandomImageUrl(width: Int = 400, height: Int = 400): String {
  return "https://source.unsplash.com/random/${width}x${height}?rid=${Random.nextInt()}"
}

@Composable
fun Content() {
  val items by remember {
    mutableStateOf(List(100) { it })
  }
  LazyColumn() {
    items(items.size) {
      if (it % 10 == 0) {
        RandomWebview()
      } else {
        RandomImage(i = it)
      }
    }
  }
}

@Composable
fun RandomWebview() {
  val state = rememberWebViewState(getRandomImageUrl(height = 200))
  Box() {
    WebView(modifier = Modifier
      .fillMaxWidth()
      .height(200.dp), state = state)
    Text(text = "WEBVIEW", color = colorResource(id = R.color.white))
  }
}

@Composable
fun RandomImage(i: Int) {
  AsyncImage(
    modifier = Modifier.size(400.dp),
    model = getRandomImageUrl(),
    contentDescription = "Image $i",
    placeholder = ColorPainter(colorResource(id = R.color.transparent)
    )
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  Pixel7CrashtestTheme {
    Content()
  }
}