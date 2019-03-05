package com.example.mvvmdemo.viewmodels

import com.example.mvvmdemo.misc.logDebug

/**
 * View-model class with properties for basic data binding.
 *
 * Note: these properties are non-observable, so modifying them will not update the UI
 */
class BasicProductsListViewModel {

  var title: String? = null

  var filterText: String? = null

  fun onCartClicked() {
    // Note: Changing the title here is an artificial example, purely to illustrate that modifying
    // the title property does not updates the UI
    title = filterText // NOTE: view doesn't get updated

    logDebug("onCartClicked! filterText=$filterText title=$title")
  }

}
