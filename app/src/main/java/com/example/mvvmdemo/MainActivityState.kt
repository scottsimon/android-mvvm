package com.example.mvvmdemo

import android.arch.lifecycle.ViewModel
import com.example.mvvmdemo.messaging.MessageFactoryImpl
import com.example.mvvmdemo.models.Cart
import com.example.mvvmdemo.models.Store

/**
 * Android architecture component used to hold onto the data/state of the [MainActivity].
 */
class MainActivityState : ViewModel() {

  lateinit var store: Store
  lateinit var cart: Cart
  lateinit var messageFactory: MessageFactoryImpl

  var currentViewModel: com.example.mvvmdemo.mvvm.ViewModel? = null

  val isInitialized: Boolean
      get() = ::store.isInitialized

  fun initialize(store: Store, cart: Cart, messageFactory: MessageFactoryImpl) {
    this.store = store
    this.cart = cart
    this.messageFactory = messageFactory
  }

}
