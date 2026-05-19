package com.prosolution.network

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class RealtimeSocketClient(
    private val okHttpClient: OkHttpClient
) {
    fun observe(url: String): Flow<String> = callbackFlow {
        var webSocket: WebSocket? = null
        val request = Request.Builder().url(url).build()
        val listener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                trySend(text)
            }
        }
        webSocket = okHttpClient.newWebSocket(request, listener)

        awaitClose {
            webSocket?.close(1000, "closed")
        }
    }
}
