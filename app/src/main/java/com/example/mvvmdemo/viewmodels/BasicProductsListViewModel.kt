package com.example.mvvmdemo.viewmodels

import com.example.mvvmdemo.misc.logDebug

/**
 * View-model class with properties for basic data binding.
 */
class BasicProductsListViewModel {

  var title: String? = null

  var filterText: String? = null

  fun onCartClicked() {
    title = filterText // NOTE: view doesn't get updated

    logDebug("onCartClicked! filterText=$filterText title=$title")
  }

}
