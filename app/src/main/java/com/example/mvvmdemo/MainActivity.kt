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
import com.example.mvvmdemo.models.Cart
import com.example.mvvmdemo.models.Store
import com.example.mvvmdemo.mvvm.createView
import com.example.mvvmdemo.viewmodels.BaseProductListViewModel
import com.example.mvvmdemo.viewmodels.BasicProductsListViewModel
import com.example.mvvmdemo.viewmodels.ObservableProductsListViewModel
import com.example.mvvmdemo.viewmodels.ProductListViewModel

private enum class METHOD { BASIC, OBSERVABLE, VIEW_MODEL_BASE_CLASS, FULL }

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)

    // get the FrameLayout that will hold/contain our (view-model) content
    val contentFrame = findViewById<FrameLayout>(R.id.content_frame)

    // ----------------------------------------------------------------------
    // Item 1: Android ViewModel architecture component
    // ----------------------------------------------------------------------
    val state = getActivityState()
    updateActivityState(state)

    // ----------------------------------------------------------------------
    // Presenting view-model content
    // ----------------------------------------------------------------------
    val method = METHOD.FULL

    when (method) {
      // ----------------------------------------------------------------------
      // Item 2: Basic Android data binding
      // ----------------------------------------------------------------------
      METHOD.BASIC -> createAndShowBasicViewModel(state, contentFrame)

      // ----------------------------------------------------------------------
      // Item 3: Observable view-model class
      // ----------------------------------------------------------------------
      METHOD.OBSERVABLE -> createAndShowObservableViewModel(state, contentFrame)

      // ----------------------------------------------------------------------
      // Item 4: ViewModel base class and binding adapters
      // ----------------------------------------------------------------------
      METHOD.VIEW_MODEL_BASE_CLASS -> createAndShowBaseClassViewModel(state, contentFrame)

      // ----------------------------------------------------------------------
      // Item 5: Recycler view items and nested view-models
      // ----------------------------------------------------------------------
      METHOD.FULL -> createAndShowProductListViewModel(state, contentFrame)
    }
  }

  //region Item 1: Android ViewModel architecture component

  private fun getActivityState(): MainActivityState {
    val mainActivityState = ViewModelProviders.of(this).get(MainActivityState::class.java)

    // If this is the first time the state was created, create our intial data/models
    if (!mainActivityState.isInitialized) {
      initActivityState(mainActivityState)
    }

    return mainActivityState
  }

  private fun initActivityState(state: MainActivityState) {
    val store = Store()
    val cart = Cart()
    val messageFactory = createMessageFactory()
    state.initialize(store, cart, messageFactory)
  }

  //endregion Item 1: Android ViewModel architecture component

  //region Item 2: Basic Android data binding

  private fun createAndShowBasicViewModel(state: MainActivityState, frameLayout: FrameLayout) {
    if (state.basicViewModel == null) {
      state.basicViewModel = BasicProductsListViewModel().apply {
        title = "Products (Basic)"
      }
    }

    val binding = DataBindingUtil.inflate<ViewDataBinding>(
      LayoutInflater.from(this),
      R.layout.basic_product_list_view_model,
      frameLayout,
      false
    )
    binding.setVariable(BR.viewModel, state.basicViewModel)
    binding.executePendingBindings()

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
    binding.executePendingBindings()

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
      state.currentViewModel = ProductListViewModel(state.store, state.cart, state.messageFactory).apply {
        title = "Products (Recycler View)"
      }
    }

    createView(frameLayout, state.currentViewModel)
  }

  //endregion

  //region Message factory

  private fun createMessageFactory(): MessageFactoryImpl {
    return MessageFactoryImpl().apply {
      updateMessageFactory(this)
    }
  }

  private fun updateActivityState(state: MainActivityState) {
    state.messageFactory?.let {
      updateMessageFactory(it)
    }
  }

  private val contextProvider: () -> Context? = { this }

  private fun updateMessageFactory(messageFactoryImpl: MessageFactoryImpl) {
    // Update the message factory to use us (the current Activity) as its context when showing messages
    messageFactoryImpl.contextProvider = contextProvider
  }

  //endregion Message factory

}

