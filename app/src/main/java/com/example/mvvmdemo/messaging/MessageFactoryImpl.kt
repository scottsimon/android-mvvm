package com.example.mvvmdemo.messaging

import android.content.Context

class MessageFactoryImpl: MessageFactory {

  var contextProvider: (() -> Context?)? = null

  override fun showTransientMessage(message: String) {
    val context = contextProvider?.invoke() ?: throw IllegalStateException("No activity provided!")

    ToastMessage(context, message).show()
  }

}
