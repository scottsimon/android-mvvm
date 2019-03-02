package com.example.mvvmdemo.viewmodels

import com.example.mvvmdemo.R
import com.example.mvvmdemo.misc.logDebug
import com.example.mvvmdemo.models.Product
import com.example.mvvmdemo.mvvm.ViewModel

class ProductSummaryViewModel(val product: Product): ViewModel(R.layout.product_summary_view_model) {

  val title: String = product.name

  fun onClick() {
    logDebug("${product.name} clicked!")
  }

  fun onAddClicked() {
    logDebug("Add clicked for ${product.name}!")
  }

}
