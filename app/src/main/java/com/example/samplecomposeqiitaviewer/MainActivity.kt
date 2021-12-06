package com.example.samplecomposeqiitaviewer

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.samplecomposeqiitaviewer.entity.QiitaArticle
import com.example.samplecomposeqiitaviewer.nw.QiitaApi
import com.example.samplecomposeqiitaviewer.ui.SampleComposeQiitaViewerTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleComposeQiitaViewerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val qiitaArticles = remember { mutableStateListOf<QiitaArticle>() }
    val selectedArticle = remember { mutableStateOf<QiitaArticle?>(null) }

    MaterialTheme {
        Scaffold(

        ) {
            Row {
                Box(modifier = Modifier.weight(1.0F)) {
                    ListScreen(
                        items = qiitaArticles,
                        onItemsChanged = {
                            MainScope().launch {
                                try {
                                    val articles = QiitaApi().getAllArticles()
                                    Log.d("QiitaViewer", "FAB: $articles")
                                    qiitaArticles.clear()
                                    qiitaArticles.addAll(articles)

                                } catch (e: Exception) {
                                    Log.e("QiitaViewer", "FAB: Exception", e)
                                }
                            }
                        },
                        onItemSelected = {
                            selectedArticle.value = it }
                    )
                }
                Box(modifier = Modifier.weight(1.0F)) {
                    DetailScreen(url = selectedArticle.value?.url ?: "")
                }
            }
        }
    }
}

@Composable
fun ListScreen(items: List<QiitaArticle>, onItemsChanged:()-> Unit, onItemSelected : (QiitaArticle)->Unit) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onItemsChanged,
                text = { Text("reload") },
                icon = { Icon(imageVector = Icons.Filled.Refresh, contentDescription = null) },
                modifier = Modifier.semantics { contentDescription = "Refresh Button" }
            )
        }
    ) {
        QiitaItemList(items = items, onItemSelected = onItemSelected)
    }
}

@Composable
fun DetailScreen(url: String) {
    if (url.isEmpty()) {
        Text("ここに選んだページが表示されるイメージ")
    } else {
        MyWebComposable(url)
    }
}

@Composable
private fun MyWebComposable(url: String) {
    AndroidView(factory = { ctx ->
        WebView(ctx)
    }, update = {
        it.webViewClient = WebViewClient()
        it.loadUrl(url)
    }, modifier = Modifier.fillMaxSize()
    )
}

@Preview
@Composable
fun PrevDetailScreen() {
    DetailScreen(url = "http://google.co.jp/")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SampleComposeQiitaViewerTheme {
        HomeScreen()
    }
}

@Composable
fun QiitaItem(title: String, url: String, modifier: Modifier) {
    Row(modifier = modifier)
    {
        Column {
            Text(text = title, style = typography.h6)
            Text(text = url, style = typography.body2)
        }
    }
}

@Composable
fun QiitaItemList(items: List<QiitaArticle>, onItemSelected: (QiitaArticle) -> Unit) {
    LazyColumn(
        modifier = Modifier.semantics { contentDescription = "Item List" }) {
        items(items) { item ->
            QiitaItem(title = item.title, url = item.url, modifier = Modifier.clickable(onClick = {
                onItemSelected(item) }))
            Divider(color = Color.Black)
        }
    }
}


@Preview
@Composable
fun prevQiitaItem() {
    QiitaItem("title1", "http://sample.co.jp/", modifier = Modifier)
}



@Preview
@Composable
fun MyWebPrev() {
    val weburl = remember { mutableStateOf("")}
    Column {
        Row {
            Button(onClick = { weburl.value = "http://google.co.jp/" }) {
               Text("Url1")
            }
            Button(onClick = { weburl.value = "http://yahoo.co.jp/" }) {
                Text("Url2")
            }
        }
        MyWebComposable(url = weburl.value)
    }
}