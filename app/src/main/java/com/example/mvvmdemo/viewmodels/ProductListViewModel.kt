package com.example.mvvmdemo.viewmodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.mvvmdemo.BR
import com.example.mvvmdemo.misc.logDebug

class ProductListViewModel : BaseObservable() {

  //region Phase 1

//  var title: String? = null
//
//  var filterText: String? = null

  //endregion Phase 1

  //region Phase 2 - bindable properties

  @Bindable
  var title: String? = null
    set(value) {
      field = value
      notifyPropertyChanged(BR.title) // BR.title auto-generated
    }

  @Bindable
  var filterText: String? = ""
    set(value) {
      field = value
      notifyPropertyChanged(BR.filterText) // BR.title auto-generated
    }

  //endregion

  fun onCartClicked() {
    logDebug("onCartClicked! filter text=$filterText")

    // Phase 2
    title = filterText
  }

}
