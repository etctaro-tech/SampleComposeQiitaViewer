package com.example.samplecomposeqiitaviewer

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.semantics.accessibilityLabel
import androidx.compose.ui.semantics.semantics
import androidx.core.content.ContextCompat.startActivity
import androidx.ui.tooling.preview.Preview
import com.example.samplecomposeqiitaviewer.mylibrary.entity.QiitaArticle
import com.example.samplecomposeqiitaviewer.mylibrary.nw.QiitaApi
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

    MaterialTheme {
        Scaffold(
            bodyContent = {
                QiitaItemList(items = qiitaArticles)
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
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
                    text = { Text("reload") },
                    icon = { Icon(asset = Icons.Filled.Refresh) },
                    modifier = Modifier.semantics { accessibilityLabel = "Refresh Button" }
                )
            }
        )
    }
}

@Preview
@Composable
fun PrevHomeScreen() {
    MaterialTheme {
        Scaffold(
            bodyContent = {
                QiitaItemList(items = listOf())
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {},
                    text = { Text("reload") },
                    icon = { Icon(asset = Icons.Filled.Refresh) }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SampleComposeQiitaViewerTheme {
        HomeScreen()
    }
}

@Composable
fun QiitaItem(title: String, url: String) {
    val context: Context = ContextAmbient.current

    Row(Modifier.clickable(onClick = {
        val uri = Uri.parse(url)
        val intent = Intent(ACTION_VIEW, uri)
        startActivity(context, intent, null)
    })) {
        Column {
            Text(text = title, style = typography.h6)
            Text(text = url, style = typography.body2)
        }
    }
}

@Composable
fun QiitaItemList(items: List<QiitaArticle>) {
    ScrollableColumn(
        modifier = Modifier.semantics { accessibilityLabel = "Item List" }) {
        for (item in items) {
            QiitaItem(title = item.title, url = item.url)
            Divider(color = Color.Black)
        }
    }
}


@Preview
@Composable
fun prevQiitaItem() {
    QiitaItem("title1", "http://sample.co.jp/")
}

@Preview
@Composable
fun prevQiitaItemList() {
    QiitaItemList(
        listOf(
            QiitaArticle("001", "title1", "http://sample.co.jp/1"),
            QiitaArticle("002", "title2", "http://sample.co.jp/2"),
            QiitaArticle("003", "title3", "http://sample.co.jp/3"),
        )
    )
}
