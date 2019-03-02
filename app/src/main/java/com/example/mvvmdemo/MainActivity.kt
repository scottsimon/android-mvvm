package com.example.mvvmdemo

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.mvvmdemo.databinding.ProductListViewModelBinding
import com.example.mvvmdemo.messaging.MessageFactory
import com.example.mvvmdemo.messaging.MessageFactoryImpl
import com.example.mvvmdemo.models.Cart
import com.example.mvvmdemo.models.Store
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

    //region Phase 2. Simple data binding example

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

    //endregion

    //region Phase 3. Binding adapters & ViewModel base class
    //
    createView(contentFrame, productsViewModel)

    //endregion

    // Phase 4.
  }

  private fun getState(): MainActivityState {
    // Use Android's ViewModel architecture component "to store and manage UI-related data in a lifecycle conscious way."
    //
    // https://developer.android.com/reference/androidx/lifecycle/ViewModel.html
    //
    val mainActivityState = ViewModelProviders.of(this).get(MainActivityState::class.java)

    if (!mainActivityState.isInitialized) {
      // If this is the first time the state was created, create our intial state/data/view-model
      initActivityState(mainActivityState)
    } else {
      updateActivityState(mainActivityState)
    }

    return mainActivityState
  }

  private val contextProvider: () -> Context? = { this }

  private fun initActivityState(state: MainActivityState) {
    val store = Store()
    val cart = Cart()
    val messageFactory = createMessageFactory()
    val productListViewModel = createProductListViewModel(store, cart, messageFactory)
    state.initialize(store, cart, productListViewModel)
  }

  private fun createProductListViewModel(
    store: Store,
    cart: Cart,
    messageFactory: MessageFactoryImpl
  ): ProductListViewModel = ProductListViewModel(store, cart, messageFactory).apply {
    title = "My Products"
  }

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

  private fun updateMessageFactory(messageFactoryImpl: MessageFactoryImpl) {
    messageFactoryImpl.contextProvider = contextProvider
  }

  //endregion Message factory

}
