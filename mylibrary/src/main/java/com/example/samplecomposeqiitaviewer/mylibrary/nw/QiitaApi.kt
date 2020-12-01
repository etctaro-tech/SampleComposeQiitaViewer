package com.example.samplecomposeqiitaviewer.mylibrary.nw

import com.example.samplecomposeqiitaviewer.mylibrary.entity.QiitaArticle
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class QiitaApi {
    private val httpClient = HttpClient(Android) {
        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {ignoreUnknownKeys = true}

            serializer = KotlinxSerializer(
                json
            )
        }
    }

    companion object {
        private const val QIITA_URI = "https://qiita.com/api/v2/items?page=1&per_page=20"
    }

    suspend fun getAllArticles(): List<QiitaArticle> {
        return httpClient.get(QIITA_URI)
    }
}