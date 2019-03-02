package com.example.mvvmdemo.viewmodels

import android.databinding.Bindable
import com.example.mvvmdemo.BR
import com.example.mvvmdemo.R
import com.example.mvvmdemo.misc.logDebug
import com.example.mvvmdemo.models.Cart
import com.example.mvvmdemo.models.Product
import com.example.mvvmdemo.mvvm.ViewModel
import com.example.mvvmdemo.mvvm.bindable

// Phase 1/2: basic data binding
//
//class ProductListViewModel : BaseObservable() {
//
//  //region Phase 1
//
////  var title: String? = null
////
////  var filterText: String? = null
//
//  //endregion Phase 1
//
//  //region Phase 2 - bindable properties
//
//  @Bindable
//  var title: String? = null
//    set(value) {
//      field = value
//      notifyPropertyChanged(BR.title) // BR.title auto-generated
//    }
//
//  @Bindable
//  var filterText: String? = ""
//    set(value) {
//      field = value
//      notifyPropertyChanged(BR.filterText) // BR.title auto-generated
//    }
//
//  //endregion
//
//  fun onCartClicked() {
//    logDebug("onCartClicked! filter text=$filterText")
//
//    // Phase 2
//    title = filterText
//  }
//
//}


// Phase 3: binding adapters & ViewModel base class
//
//class ProductListViewModel : ViewModel(R.layout.product_list_view_model) {
//
//  @get:Bindable
//  var title: String? by bindable(BR.title, null)
//
//  @get:Bindable
//  var filterText: String? by bindable(BR.filterText, "")
//
//  fun onCartClicked() {
//    logDebug("onCartClicked! filter text=$filterText")
//
//    // Phase 2
//    title = filterText
//  }
//
//}

// Phase 4: binding adapters & ViewModel base class
//
class ProductListViewModel(private val cart: Cart) : ViewModel(R.layout.product_list_view_model) {

  @get:Bindable
  var title: String? by bindable(BR.title, null)

  @get:Bindable
  var filterText: String? by bindable(BR.filterText, "")

  val cartViewModel = TinyCartViewModel(cart).apply { onClickedCallback = { onCartClicked() } }

  fun onCartClicked() {
    logDebug("onCartClicked! filter text=$filterText")

    cart.addItem(createRandomProduct())
  }

  private fun createRandomProduct() = Product("A", "aaaa", 1.2f)

}

