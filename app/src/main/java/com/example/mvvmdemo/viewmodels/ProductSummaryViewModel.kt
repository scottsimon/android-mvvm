package com.example.mvvmdemo.viewmodels

import com.example.mvvmdemo.R
import com.example.mvvmdemo.models.Product
import com.example.mvvmdemo.mvvm.ViewModel

class ProductSummaryViewModel(val product: Product): ViewModel(R.layout.product_summary_view_model) {

  val title: String = product.name

  val description: String = product.description

  var onClickedHandler: ((ProductSummaryViewModel) -> Unit)? = null

  var onAddToCartClickedHandler: ((ProductSummaryViewModel) -> Unit)? = null

  fun onClicked() {
    onClickedHandler?.invoke(this)
  }

  fun onAddClicked() {
    onAddToCartClickedHandler?.invoke(this)
  }

}
