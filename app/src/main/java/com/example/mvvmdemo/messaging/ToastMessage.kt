package com.example.mvvmdemo.messaging

import android.content.Context
import android.widget.Toast

class ToastMessage(val context: Context, val message: String) {

  fun show() {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
  }

}
