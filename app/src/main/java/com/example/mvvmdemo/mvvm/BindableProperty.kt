package com.example.mvvmdemo.mvvm

import android.databinding.BaseObservable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/***
 * Read/Write property that automatically calls notifyPropertyChanged when value of the property changes.
 * NOTE: Uses kotlin == for comparison.
 *
 * Example usage:
 * @get:Bindable
 * var enabled by bindable(BR.enabled, true)
 *
 * Or if you need to do something when the property changes:
 * @get:Bindable
 * var enabled by bindable(BR.enabled, true) {
 *   //on changed logic
 * }
 */
open class BindableProperty<T>(
  private val propertyId: Int,
  protected var value: T,
  private val onChange: ((oldValue: T, newValue: T) -> Unit)? = null
) : ReadWriteProperty<BaseObservable, T> {

  override operator fun getValue(thisRef: BaseObservable, property: KProperty<*>): T = value

  override operator fun setValue(thisRef: BaseObservable, property: KProperty<*>, newValue: T) {
    if (value == newValue) return

    val oldValue = value

    value = newValue
    thisRef.notifyPropertyChanged(propertyId)

    onChange?.invoke(oldValue, newValue)
  }
}

fun <T> bindable(propertyId: Int, initialValue: T, onChange: ((oldValue: T, newValue: T) -> Unit)? = null) =
  BindableProperty(propertyId, initialValue, onChange)

fun <T> bindable(propertyId: Int, initialValue: T, onChange: ((newValue: T) -> Unit)) =
  BindableProperty(propertyId, initialValue, { _, newValue -> onChange.invoke(newValue) })

fun <T> bindable(propertyId: Int, initialValue: T, onChange: (() -> Unit)) =
  BindableProperty(propertyId, initialValue, { _, _ -> onChange.invoke() })
