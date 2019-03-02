package com.example.mvvmdemo.viewmodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.mvvmdemo.BR
import com.example.mvvmdemo.misc.logDebug

class ObservableProductsListViewModel: BaseObservable() {

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

  fun onCartClicked() {
    logDebug("onCartClicked! filter text=$filterText")

    title = filterText // NOTE: now the view gets updated
  }

}