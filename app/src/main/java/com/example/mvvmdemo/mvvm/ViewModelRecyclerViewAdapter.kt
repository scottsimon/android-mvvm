package com.example.mvvmdemo.mvvm

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvmdemo.BR

class BindingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val binding: ViewDataBinding? = DataBindingUtil.bind(itemView)
}

class ViewModelRecyclerViewAdapter(viewModels: List<ViewModel>?)
  : RecyclerView.Adapter<BindingViewHolder>() {

  private var viewModels: MutableList<ViewModel>? = viewModels?.toMutableList()

  override fun getItemCount(): Int = viewModels?.size ?: 0

  override fun getItemViewType(position: Int): Int = viewModels!![position].layoutResId

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
    val binding = DataBindingUtil.inflate<ViewDataBinding>(
      LayoutInflater.from(parent.context),
      viewType,
      parent,
      false
    )

    return BindingViewHolder(binding.root)
  }

  override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
    viewModels?.get(position)?.let { viewModel ->
      holder.binding?.apply {
        setVariable(BR.viewModel, viewModel)
        executePendingBindings()
      }
    }
  }

}


