package com.example.mvvmdemo

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.mvvmdemo.databinding.ProductListViewModelBinding
import com.example.mvvmdemo.viewmodels.ProductListViewModel

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)
    val contentFrame = findViewById<FrameLayout>(R.id.content_frame)

    // Step 1. Android ViewModel architecture component
    val stateViewModel = getStateViewModel()

    // Step 2. Simple data binding example
    val productsViewModel = stateViewModel.productsViewModel

    val binding = DataBindingUtil.inflate<ProductListViewModelBinding>(
      LayoutInflater.from(this),
      R.layout.product_list_view_model,
      contentFrame,
      false
    )
    binding.setVariable(BR.viewModel, productsViewModel)
    binding.executePendingBindings()

    contentFrame.addView(binding.root)

  }

  private fun getStateViewModel(): MainActivityViewModel {
    // Use Android's ViewModel architecture component "to store and manage UI-related data in a lifecycle conscious way."
    //
    // https://developer.android.com/reference/androidx/lifecycle/ViewModel.html
    //
    val mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

    // Perform any needed initialization...
    if (!mainActivityViewModel.isInitialized) {
      mainActivityViewModel.initialize(createProductListViewModel())
    }

    return mainActivityViewModel
  }

  private fun createProductListViewModel() = ProductListViewModel().apply { title = "My Products" }

}
