package com.example.mvvmdemo.viewmodels

import android.databinding.Bindable
import com.example.mvvmdemo.BR
import com.example.mvvmdemo.R
import com.example.mvvmdemo.misc.logDebug
import com.example.mvvmdemo.mvvm.ViewModel
import com.example.mvvmdemo.mvvm.bindable

/**
 * View-model class that inherits from our [ViewModel] base class.
 *
 * - Simplify boiler-plate to create/bind view via BindingAdapters.createView()
 * - Simplify boiler-plate code for observable properies with 'by bindable' ([BindableProperty])
 */
class BaseProductListViewModel : ViewModel(R.layout.base_product_list_view_model) {

  @get:Bindable
  var title: String? by bindable(BR.title, null)

  @get:Bindable
  var filterText: String? by bindable(BR.filterText, "")

  fun onCartClicked() {
    logDebug("onCartClicked! filter text=$filterText")
  }

}
