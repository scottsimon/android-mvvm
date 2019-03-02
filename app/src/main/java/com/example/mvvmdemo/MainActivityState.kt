package com.example.mvvmdemo

import android.arch.lifecycle.ViewModel
import com.example.mvvmdemo.models.Cart
import com.example.mvvmdemo.viewmodels.ProductListViewModel

/**
 * Android architecture component used to hold onto the data/state of the [MainActivity].
 */
class MainActivityState : ViewModel() {

  var cart: Cart? = null

  var productsViewModel: ProductListViewModel? = null

  val isInitialized: Boolean = cart != null

  fun initialize(cart: Cart, productsViewModel: ProductListViewModel) {
    this.cart = cart
    this.productsViewModel = productsViewModel
  }

}
