package br.com.newsoftwarebrasil.pocsmsretriever

import android.app.Application
import android.util.Log

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val appSignature = AppSignatureHelper(this)
        for (signature in appSignature.appSignatures) {
            Log.e(TAG, "onCreate: $signature")
        }
    }

    companion object {
        private const val TAG = "MyApplication"
    }
}