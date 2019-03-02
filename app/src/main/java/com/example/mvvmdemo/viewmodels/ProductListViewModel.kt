package com.example.mvvmdemo.viewmodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.mvvmdemo.BR
import com.example.mvvmdemo.R
import com.example.mvvmdemo.messaging.MessageFactory
import com.example.mvvmdemo.misc.logDebug
import com.example.mvvmdemo.models.Cart
import com.example.mvvmdemo.models.Product
import com.example.mvvmdemo.models.Store
import com.example.mvvmdemo.mvvm.ViewModel
import com.example.mvvmdemo.mvvm.bindable

// Item 4: ViewModel base class and binding adapters

class ProductListViewModel : ViewModel(R.layout.product_list_view_model) {

  @get:Bindable
  var title: String? by bindable(BR.title, null)

  @get:Bindable
  var filterText: String? by bindable(BR.filterText, "")

  fun onCartClicked() {
    logDebug("onCartClicked! filter text=$filterText")
  }

}

// Phase 4: binding adapters & ViewModel base class
//
//class ProductListViewModel(
//  private val store: Store,
//  private val cart: Cart,
//  private val messageFactory: MessageFactory
//) : ViewModel(R.layout.product_list_view_model) {
//
//  @get:Bindable
//  var title: String? by bindable(BR.title, null)
//
//  @get:Bindable
//  var filterText: String? by bindable(BR.filterText, "")
//
//  val cartViewModel = TinyCartViewModel(cart).apply { onClickedHandler = { showCart() } }
//
//  val productViewModels: List<ProductSummaryViewModel> = createSummaryViewModels()
//
//  private fun showCart() {
//    logDebug("showCart: ${cart.items.size} items in the cart")
//    // TODO: show cart screen/view-model
//  }
//
//  private fun createSummaryViewModels(): List<ProductSummaryViewModel> {
//    return store.products.map { product ->
//      createSummaryViewModel(product)
//    }
//  }
//
//  private fun createSummaryViewModel(product: Product): ProductSummaryViewModel {
//    return ProductSummaryViewModel(product).apply {
//      //region Wire up callbacks
//      onClickedHandler = { productSummaryViewModel ->
//        showProductDetails(productSummaryViewModel.product)
//      }
//
//      onAddToCartClickedHandler = { productSummaryViewModel ->
//        addProductToCart(productSummaryViewModel.product)
//      }
//      //endregion
//    }
//  }
//
//  //region Product summary handlers
//
//  private fun showProductDetails(product: Product) {
//    logDebug("showProductDetails: ${product.name}")
//    // TODO: show product details screen/view-model
//  }
//
//  private fun addProductToCart(product: Product) {
//    cart.addItem(product)
//
//    messageFactory.showTransientMessage("${product.name} added to cart")
//  }
//
//  //endregion
//
//}

