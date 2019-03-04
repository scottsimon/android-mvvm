package com.example.mvvmdemo

import android.arch.lifecycle.ViewModel
import com.example.mvvmdemo.messaging.MessageFactoryImpl
import com.example.mvvmdemo.models.Cart
import com.example.mvvmdemo.models.Store

/**
 * Android architecture component used to hold onto the data/state of the [MainActivity].
 */
class MainActivityState : ViewModel() {

  val store: Store = Store()
  val cart: Cart = Cart()

  // Hold onto the currently displayed view-model
  var basicViewModel: Any? = null
  var currentViewModel: com.example.mvvmdemo.mvvm.ViewModel? = null

  //region Message factory

  var messageFactory: MessageFactoryImpl? = null

  //endregion

}


