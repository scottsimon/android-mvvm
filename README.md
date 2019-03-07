# Description

This is a sample application used for a presentation at the 2019 Esri DevSummit to demonstrate basic Android data binding and how to apply the MVVM pattern on Android.

Below is an outline of the key points covered during the presentation and is a good step-by-step guide to incrementally explain data binding and MVVM.


# Outline

## Item 1. Android ViewModel architecture component

### 1-1. The `android.arch.lifecycle.ViewModel` class
> “The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.”  

Even though this is named "ViewModel", it is not the same "ViewModel" used in MVVM _(IMO)_

### 1-2. `MainActivityState` (our ViewModel class)

The `MainActivityState` class contains our application/UI data/state. To create/get the one instance (in `MainActivity.getActivityState()`):  


```
ViewModelProviders.of(this).get(MainActivityState::class.java)
```

This:
> "Returns an existing ViewModel or creates a new one in the scope (usually, a fragment or an activity), associated with this ViewModelProvider"  


## Item 2. Basic Android Data Binding  


### 2-1. Basic view-model class 

`BasicProductsListViewModel.kt`  
- `title` & `filterText` properties  
- `onCartClicked()` function

### 2-2. Data binding layout resource 

In `R.layout.basic_product_list_view_model`, notice the following new XML elements:  

`<layout>`: new root element  
`<data>`: block to declare one or more variables which can be referenced (bound) in the remainder of the layout  
`<variable>`: name/type of variable  

**One-way binding:**
 `android:text="@{viewModel.title}"`

**Two-way binding:**
    `android:text="@={viewModel.filterText}"`

**Binding events/methods:**
    `android:onClick="@{() -> viewModel.onCartClicked()}"`


### 2-3. Inflating the data binding layout

In `MainActivity.createAndShowBasicViewModel()`:  
```
val binding = DataBindingUtil.inflate<ViewDataBinding>(..., R.layout.basic_product_list_view_model, ...)
```  
This inflates the data binding layout (similar to inflating traditional layout) and returns an object that is a subclass of `ViewDataBinding`. This class is auto-generated class by compiler (e.g., `BasicProductsListViewModelBindingImpl`). 

The `ViewDataBinding` class contains:
- A `root` property: the root view/element from layout  
- One mutable property for each `<variable>` defined in the `<data>` block. 
- `setVariable()`: sets the value of the data binding's variable/property in a generic way 

### 2-4. Binding the view-model to the `ViewDataBinding`
```
binding.setVariable(BR.viewModel, state.basicViewModel)
```

Sets the value of the `viewModel` property of our data binding to the specific instance of our view-model.
 
 ### 2-5. Displaying the inflated data binding layout view

```
frameLayout.addView(binding.root)
```

Adds root view from the data binding layout to our `ViewGroup`. 

**Note:** `BasicProductsListViewModel.onCartClicked()` modifies the `title` property, but notice that the UI/`TextView` is not updated to reflect this change. This is because `BasicProductsListViewModel` does not notify the UI/`TextView` that the property has changed.


## Item 3: Observable view-model class

The `ObservableProductsListViewModel` is similar to the `BasicProductsListViewModel` class, except that it:  
- Inherits from `BaseObservable`  
- Property setters call `notifyPropertyChanged(BR.title)`

```
  @Bindable
  var title: String? = null
    set(value) {
      field = value
      notifyPropertyChanged(BR.title) // BR.title auto-generated
    }
```

`notifyPropertyChanged()` notifies any listeners--e.g., the UI/`TextView`--that a property has changed. Now the UI/`TextView` will be updated whenever the property changes.   


## Item 4: Eliminating boilerplate and introducing binding adapters

### 4-1. Simplifying observable properties

Subclasses of `BaseObservable` require lots of boilerplate code (every setter needs to call `notifyPropertyChanged()`). The `BaseProductListViewModel` class inherits from a new `ViewModel` base class. The `by bindable` top-level function uses Kotlin property delegation (with our `BindableProperty`) to encapsulate `notifyPropertyChanged()` boilerplate code:

 
```
  @get:Bindable
  var title: String? by bindable(BR.title, null)
```
    
### 4-2. Simplifying inflating a view-model's view

In `MainActivity.createAndShowBaseClassViewModel()`:

```
    createView(frameLayout, state.currentViewModel)
```

...inflates the view for the given view-model, and adds that view to the view-group. 

The `createView(ViewGroup, ViewModel?)` method uses the `ViewModel.layoutResId` to inflate the data binding layout resource corresponding to the view-model, and sets the view-model variable into the view data binding. This is essentially the same (boilerplate code) shown in 2-3, 2-4, and 2-5. 

**Note: we follow the convention of defining a variable named `viewModel` in our view data binding layouts, which `createView()` assumes exists in order for this function work properly.**



## Item 5: Common patterns/scenarios with MVVM

### 5-1. Nesting view-models in other view-models 

The `TinyCartViewModel` class encapsulates the cart view (`ImageButton`) with a counter.

The `ProductListViewModel` class has a `cartViewModel: TinyCartViewModel` property.  

In `R.layout.product_list_view_model` we can easily display the `TinyCartViewModel`'s view within our view using data binding:  

```
<FrameLayout
  ... 
  bind:createView="@{viewModel.cartViewModel} 
  />"
```

The `bind:createView` is not an existing property on a `FrameLayout`. It is a **binding adapter**. This is a top-level function (`static` function in Java) that is annotated with the `@BindingAdapter("createView")` annotation:

```
@BindingAdapter("createView")
fun createView(viewGroup: ViewGroup, viewModel: ViewModel?) { ... }
```

The data binding library will call this function anytime the bound value is modified. 

Notice how the `TinyCartViewModel`'s view is now displayed within the `ProductListViewModel`'s view. 

Also notice: 
- Clicking "+" button for a product adds item to cart and the counter is updated  
- When rotating the device, even though the Activity is destroyed/re-created, the cart count is maintained.

### 5-2. Recycler views

We frequently want to display a number of items/views--in this case, view-models--inside a `RecyclerView`. To simplify this, we have created a binding adapter with a custom `RecyclerView.Adapter`. 

The `ProductListViewModel` class contains a collection of `ProductSummaryViewModel`:
```
`ProductListViewModel.productViewModels: List<ProductSummaryViewModel>`  
```

In the `R.layout.product_list_view_model`, we can bind that collection to a `RecyclerView`:  
```
<RecyclerView 
  ... 
  bind:recyclerViewAdapter="@{viewModel.productViewModels}" 
  />
```

The binding adapter is defined in `BindingAdapters.kt`:  
```
@BindingAdapter("recyclerViewAdapter")
fun bindRecyclerViewAdapter(recyclerView: RecyclerView, viewModels: List<ViewModel>?) {
  val adapter = ViewModelRecyclerViewAdapter(viewModels)
  recyclerView.adapter = adapter
}
```

In the `ViewModelRecyclerViewAdapter` class:   
- `getItemViewType()`: uses `ViewModel.layoutResId` as unique "item type"  
- `onCreateViewHolder()`: inflates the data binding layout  
- `onBindViewHolder()`: sets `ViewModel` instance into `ViewDataBinding` variable  

### 5-3. Filtering the list of products

`ProductListViewModel`  
- `var filterText: String?`: two-way binding modifies this when `EditText` text changes  
- `onFilterTextChanged()` / `filterProducts()`: filters list of products; updates the `productViewModels` property  
- `bind:recyclerViewAdapter` binding adapter will be re-executed when `productViewModels` changes  


`ProductListViewModel`'s `filterText` property is updated (via two-way binding) whenever the `EditText`'s text changes. Our `by bindable` extension function takes a `onChange` lambda parameter, that is invoked anytime the property changes:

```
var filterText: String? by bindable(BR.filterText, "", this::onFilterTextChanged)
```

The `onFilterTextChanged()` function filters the list of view-models, and updates the `productViewModels` property (which triggers the `recyclerViewAdapter` binding to be re-executed):
```
private fun onFilterTextChanged(oldValue: String?, newValue: String?) {
  productViewModels = filterProducts(newValue)
}
```


# Questions
If you have any questions, please email me at ssimon@esri.com

