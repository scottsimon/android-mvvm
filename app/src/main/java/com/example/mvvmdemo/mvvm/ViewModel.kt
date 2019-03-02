package com.example.mvvmdemo.mvvm

import android.databinding.BaseObservable
import android.support.annotation.LayoutRes

open class ViewModel(@LayoutRes val layoutResId: Int) : BaseObservable()
