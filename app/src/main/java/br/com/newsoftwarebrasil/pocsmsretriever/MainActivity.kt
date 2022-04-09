package br.com.newsoftwarebrasil.pocsmsretriever

import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever

class MainActivity : AppCompatActivity() {
    private var intentFilter: IntentFilter? = null
    private var appSMSBroadcastReceiver: AppSMSBroadcastReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        smsListener()
        initBroadCast()
        val appSignature = AppSignatureHelper(this)
        for (signature in appSignature.appSignatures) {
            val textView = findViewById<TextView>(R.id.tvTitle)
            textView.text = "${textView.text}\n$signature"
        }
    }

    private fun initBroadCast() {
        intentFilter = IntentFilter("com.google.android.gms.auth.api.phone.SMS_RETRIEVED")
        appSMSBroadcastReceiver = AppSMSBroadcastReceiver()
        appSMSBroadcastReceiver!!.setOnSmsReceiveListener { code ->
            Toast.makeText(
                this@MainActivity,
                code,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun smsListener() {
        val client = SmsRetriever.getClient(this)
        client.startSmsRetriever()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(appSMSBroadcastReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(appSMSBroadcastReceiver)
    }
}