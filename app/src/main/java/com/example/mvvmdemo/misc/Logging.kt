package com.example.mvvmdemo.misc

import android.util.Log

fun Any.logDebug(message: String) = Log.d(this.shortClassName, message)

private val Any.shortClassName: String
  get() = javaClass.simpleName.take(23)
