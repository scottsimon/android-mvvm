package com.example.mvvmdemo.viewmodels

import com.example.mvvmdemo.misc.logDebug

class BasicProductsListViewModel {

  var title: String? = null

  var filterText: String? = null

  fun onCartClicked() {
    logDebug("onCartClicked! filter text=$filterText")

    title = filterText // NOTE: view doesn't get updated
  }

}
