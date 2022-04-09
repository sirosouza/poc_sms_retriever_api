package br.com.newsoftwarebrasil.pocsmsretriever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class AppSMSBroadcastReceiver : BroadcastReceiver() {
    private lateinit var onSmsReceiveListener: (String?) -> Unit

    fun setOnSmsReceiveListener(onSmsReceiveListener: (String?) -> Unit) {
        this.onSmsReceiveListener = onSmsReceiveListener
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val status = extras!![SmsRetriever.EXTRA_STATUS] as Status?
            when (status!!.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val message = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String?
                    onSmsReceiveListener.invoke(message)
                }
                CommonStatusCodes.TIMEOUT -> {}
            }
        }
    }

    interface OnSmsReceiveListener {
        fun onReceive(code: String?)
    }
}