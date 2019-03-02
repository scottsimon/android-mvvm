package com.example.mvvmdemo

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.mvvmdemo.databinding.ProductListViewModelBinding
import com.example.mvvmdemo.mvvm.createView
import com.example.mvvmdemo.viewmodels.ProductListViewModel

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)

    // get the FrameLayout that will hold/contain our (view-model) content
    val contentFrame = findViewById<FrameLayout>(R.id.content_frame)

    // Phase 1. Android ViewModel architecture component
    //
    val state = getState()
    val productsViewModel = state.productsViewModel

    // Phase 2. Simple data binding example
    //
//    val binding = DataBindingUtil.inflate<ProductListViewModelBinding>(
//      LayoutInflater.from(this),
//      R.layout.product_list_view_model,
//      contentFrame,
//      false
//    )
//    binding.setVariable(BR.viewModel, productsViewModel)
//    binding.executePendingBindings()
//
//    contentFrame.addView(binding.root)

    // Phase 3. Binding adapters & ViewModel base class
    //
    createView(contentFrame, productsViewModel)

    // Phase 4.
  }

  private fun getState(): MainActivityState {
    // Use Android's ViewModel architecture component "to store and manage UI-related data in a lifecycle conscious way."
    //
    // https://developer.android.com/reference/androidx/lifecycle/ViewModel.html
    //
    val mainActivityState = ViewModelProviders.of(this).get(MainActivityState::class.java)

    // If this is the first time the state was created, create our intial state/data/view-model
    if (!mainActivityState.isInitialized) {
      mainActivityState.initialize(createProductListViewModel())
    }

    return mainActivityState
  }

  private fun createProductListViewModel() = ProductListViewModel().apply { title = "My Products" }


}
