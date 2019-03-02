package com.example.mvvmdemo.models

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.mvvmdemo.BR

class Cart: BaseObservable() {

  private val products = mutableListOf<Product>()

  val items: List<Product>
    get() = products

  @get:Bindable
  val numberOfItems: Int
    get() = products.size

  fun addItem(product: Product) {
    products.add(product)
    notifyPropertyChanged(BR.numberOfItems)
  }

}
