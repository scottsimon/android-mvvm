package com.example.mvvmdemo

import android.arch.lifecycle.ViewModel
import com.example.mvvmdemo.viewmodels.ProductListViewModel

/**
 * Android architecture component used to hold onto the data/state of the [MainActivity].
 */
class MainActivityState : ViewModel() {

  var productsViewModel: ProductListViewModel? = null

  val isInitialized: Boolean = productsViewModel != null

  fun initialize(productsViewModel: ProductListViewModel) {
    this.productsViewModel = productsViewModel
  }

}
