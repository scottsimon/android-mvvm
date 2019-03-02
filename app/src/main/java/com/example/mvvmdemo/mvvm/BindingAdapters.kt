package com.example.mvvmdemo.mvvm

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvmdemo.BR

@BindingAdapter("createView")
fun createView(viewGroup: ViewGroup, viewModel: ViewModel?) {
  viewGroup.removeAllViews()

  if (viewModel == null) {
    return  // nothing to show
  }

  val view = createView(viewGroup.context, viewGroup, viewModel)

  viewGroup.addView(view)
}

/**
 * Creates and returns a [View] for a given [ViewModel] and binds the two together.
 */
fun createView(context: Context, parent: ViewGroup, viewModel: ViewModel): View? {
  val binding = DataBindingUtil.inflate<ViewDataBinding>(
    LayoutInflater.from(context),
    viewModel.layoutResId,
    parent,
    false
  )

  binding.setVariable(BR.viewModel, viewModel) // BR.viewModel is our naming convention
  binding.executePendingBindings()

  return binding.root
}
