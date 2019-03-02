package com.example.mvvmdemo.viewmodels

import android.databinding.Bindable
import android.databinding.Observable
import com.example.mvvmdemo.BR
import com.example.mvvmdemo.R
import com.example.mvvmdemo.models.Cart
import com.example.mvvmdemo.mvvm.ViewModel

class TinyCartViewModel(val cart: Cart): ViewModel(R.layout.tiny_cart_view_model) {

  @get:Bindable
  val count: String?
    get() = cart.numberOfItems.toString()

  var onClickedCallback: (() -> Unit)? = null

  init {
    cart.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
      override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
        notifyPropertyChanged(BR.count)
      }
    })
  }

  fun onClicked() {
    onClickedCallback?.invoke()
  }

}
