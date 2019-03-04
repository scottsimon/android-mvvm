package com.example.mvvmdemo.viewmodels

import android.databinding.Bindable
import com.example.mvvmdemo.BR
import com.example.mvvmdemo.R
import com.example.mvvmdemo.messaging.MessageFactory
import com.example.mvvmdemo.models.Cart
import com.example.mvvmdemo.models.Product
import com.example.mvvmdemo.models.Store
import com.example.mvvmdemo.mvvm.ViewModel
import com.example.mvvmdemo.mvvm.bindable

class ProductListViewModel(
  private val store: Store,
  private val cart: Cart,
  private val messageFactory: MessageFactory?
) : ViewModel(R.layout.product_list_view_model) {

  private val allProductViewModels: List<ProductSummaryViewModel> = createSummaryViewModels()

  @get:Bindable
  var title: String? by bindable(BR.title, null)

  //region Nested view-model

  val cartViewModel = TinyCartViewModel(cart).apply { onClickedHandler = { showCart() } }

  private fun showCart() {
    messageFactory?.showTransientMessage("Cart has ${cart.items.size} items")
    // TODO: show cart screen/view-model
  }

  //endregion

  //region Recycler view items

  @get:Bindable
  var productViewModels: List<ProductSummaryViewModel>
      by bindable(BR.productViewModels, allProductViewModels)

  private fun createSummaryViewModels(): List<ProductSummaryViewModel> {
    return store.products.map { product ->
      createSummaryViewModel(product)
    }
  }

  private fun createSummaryViewModel(product: Product): ProductSummaryViewModel {
    return ProductSummaryViewModel(product).apply {
      onClickedHandler = { productSummaryViewModel ->
        showProductDetails(productSummaryViewModel.product)
      }

      onAddToCartClickedHandler = { productSummaryViewModel ->
        addProductToCart(productSummaryViewModel.product)
      }
    }
  }

  private fun showProductDetails(product: Product) {
    messageFactory?.showTransientMessage("Show details for product: ${product.name}")
    // TODO: show product details screen/view-model
  }

  private fun addProductToCart(product: Product) {
    cart.addItem(product)

    messageFactory?.showTransientMessage("${product.name} added to cart")
  }

  //endregion

  //region Filtering

  @get:Bindable
  var filterText: String? by bindable(BR.filterText, "", this::onFilterTextChanged)

  private fun onFilterTextChanged(oldValue: String?, newValue: String?) {
    productViewModels = filterProducts(newValue)
  }

  private fun filterProducts(filter: String?): List<ProductSummaryViewModel> {
    val text = filter ?: return allProductViewModels

    return allProductViewModels.filter { productSummaryViewModel ->
      productSummaryViewModel.description.contains(text, true)
    }
  }

  //endregion

}

