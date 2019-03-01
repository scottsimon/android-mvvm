package com.example.mvvmdemo.viewmodels

import com.example.mvvmdemo.misc.logDebug

class ProductListViewModel {

  var title: String? = null

  var filterText: String? = null

  public fun onCartClicked() {
    logDebug("onCartClicked! filter text=$filterText")
  }

}
