package com.example.mvvmdemo

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.mvvmdemo.messaging.MessageFactoryImpl
import com.example.mvvmdemo.mvvm.createView
import com.example.mvvmdemo.viewmodels.BaseProductListViewModel
import com.example.mvvmdemo.viewmodels.BasicProductsListViewModel
import com.example.mvvmdemo.viewmodels.ObservableProductsListViewModel
import com.example.mvvmdemo.viewmodels.ProductListViewModel

private enum class METHOD { BASIC, OBSERVABLE, VIEW_MODEL_BASE_CLASS, FINAL }

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)

    // get the FrameLayout that will hold/contain our (view-model) content
    val frameLayout = findViewById<FrameLayout>(R.id.content_frame)

    // ----------------------------------------------------------------------
    // Item 1: Android ViewModel architecture component
    // ----------------------------------------------------------------------
    val state = getActivityState()

    //region Presenting view-model content

//    val method = METHOD.BASIC
//    val method = METHOD.OBSERVABLE
//    val method = METHOD.VIEW_MODEL_BASE_CLASS
    val method = METHOD.FINAL

    when (method) {
      // ----------------------------------------------------------------------
      // Item 2: Basic Android data binding
      // ----------------------------------------------------------------------
      METHOD.BASIC -> createAndShowBasicViewModel(state, frameLayout)

      // ----------------------------------------------------------------------
      // Item 3: Observable view-model class
      // ----------------------------------------------------------------------
      METHOD.OBSERVABLE -> createAndShowObservableViewModel(state, frameLayout)

      // ----------------------------------------------------------------------
      // Item 4: ViewModel base class and binding adapters
      // ----------------------------------------------------------------------
      METHOD.VIEW_MODEL_BASE_CLASS -> createAndShowBaseClassViewModel(state, frameLayout)

      // ----------------------------------------------------------------------
      // Item 5: Recycler view items and nested view-models
      // ----------------------------------------------------------------------
      METHOD.FINAL -> createAndShowProductListViewModel(state, frameLayout)
    }

    //endregion
  }

  //region Item 1: Android ViewModel architecture component

  /**
   * Use the Android [ViewModel] architecture component to hold onto our data/state
   * across activity lifecycle changes.
   */
  private fun getActivityState(): MainActivityState {
    val mainActivityState = ViewModelProviders.of(this).get(MainActivityState::class.java)

    //region Message factory

    // Create a message factory if we haven't already
    if (mainActivityState.messageFactory == null) {
      mainActivityState.messageFactory = MessageFactoryImpl()
    }

    // Give the message factory a reference to the latest Activity
    mainActivityState.messageFactory?.contextProvider = contextProvider

    //endregion

    return mainActivityState
  }

  //endregion Item 1: Android ViewModel architecture component

  //region Item 2: Basic Android data binding

  private fun createAndShowBasicViewModel(state: MainActivityState, frameLayout: FrameLayout) {
    if (state.basicViewModel == null) {
      state.basicViewModel = BasicProductsListViewModel().apply { title = "Products (Basic)" }
    }

    val binding = DataBindingUtil.inflate<ViewDataBinding>(
      LayoutInflater.from(this),
      R.layout.basic_product_list_view_model,
      frameLayout,
      false
    )
    binding.setVariable(BR.viewModel, state.basicViewModel)
    //region Details
    binding.executePendingBindings()
    //endregion

    frameLayout.addView(binding.root)
  }

  //endregion

  //region Item 3: Observable view-model class

  private fun createAndShowObservableViewModel(state: MainActivityState, frameLayout: FrameLayout) {
    if (state.basicViewModel == null) {
      state.basicViewModel = ObservableProductsListViewModel().apply {
        title = "Products (Observable)"
      }
    }

    val binding = DataBindingUtil.inflate<ViewDataBinding>(
      LayoutInflater.from(this),
      R.layout.observable_products_list_view_model,
      frameLayout,
      false
    )
    binding.setVariable(BR.viewModel, state.basicViewModel)
    //region Details
    binding.executePendingBindings()
    //endregion

    frameLayout.addView(binding.root)
  }

  //endregion

  //region Item 4: ViewModel base class and binding adapters

  private fun createAndShowBaseClassViewModel(state: MainActivityState, frameLayout: FrameLayout) {
    if (state.currentViewModel == null) {
      state.currentViewModel = BaseProductListViewModel().apply {
        title = "Products (ViewModel)"
      }
    }

    createView(frameLayout, state.currentViewModel)
  }

  //endregion

  //region Item 5: Recycler view items and nested view-models

  private fun createAndShowProductListViewModel(state: MainActivityState, frameLayout: FrameLayout) {
    if (state.currentViewModel == null) {
      state.currentViewModel = ProductListViewModel(
        state.store,
        state.cart,
        state.messageFactory
      ).apply {
        title = "Products (Final)"
      }
    }

    createView(frameLayout, state.currentViewModel)
  }

  //endregion

  //region Message factory

  private val contextProvider: () -> Context? = { this }

  //endregion Message factory

}

