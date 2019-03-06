# Description

This is a sample application used for a presentation at the 2019 Esri DevSummit to demonstrate basic Android data binding and how to apply the MVVM pattern on Android.

Below is an outline of the key points covered during the presentation and is a good step-by-step guide to incrementally explain data binding and MVVM.


# Outline

## Item 1. Android ViewModel architecture component

1-1. Android ViewModel architecture component  
 - `android.arch.lifecycle.ViewModel`
 > “The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.”  
 - Even though this is named "ViewModel", it is not the same "ViewModel" in MVVM (IMO)

1-2. `MainActivity.getActivityState()`
 - `ViewModelProviders.of(this).get(MainActivityState::class.java)`  
    > "Returns an existing ViewModel or creates a new one in the scope (usually, a fragment or an activity), associated with this ViewModelProvider"  
 - `MainActivityState` class: contains our application/UI data/state


## Item 2. Basic Android Data Binding

2-1. `R.layout.basic_product_list_view_model`  

New XML elements:  
- `<layout>`: new root element
- `<data>`: block to declare one or more variables which can be referenced (bound) in the remainder of the layout
- `<variable>`: name/type of variable

One-way binding:  
 `android:text="@{viewModel.title}"`

Two-way binding:  
    `android:text="@={viewModel.filterText}"`

Binding events/methods:  
    `android:onClick="@{() -> viewModel.onCartClicked()}"`

2-2. `BasicProductsListViewModel.kt`  
Properties: `title` & `filterText`  
Function: `onCartClicked()`  

2-3. `MainActivity.createAndShowBasicViewModel()`:  
`DataBindingUtil.inflate()`: inflates the data binding layout (similar to inflating traditional layout)  
- Returns an auto-generated class by compiler (e.g., `BasicProductsListViewModelBindingImpl`)  
- A subclass of `ViewDataBinding`  

`ViewDataBinding.setVariable()`: sets the data binding's variable/property to refer to a specific view-model instance  

`ViewDataBinding.root`: the root view/element from layout  

`frameLayout.addView()`: adds data binding root view to `ViewGroup`  

2-4. Note in `BasicProductsListViewModel.onCartClicked` that modifying the `title` property does not update the UI/`TextView`. This is because `BasicProductsListViewModel` does not notify the UI/`TextView` that the property has changed.


## Item 3: Observable view-model class

3-1. `ObservableProductsListViewModel`  
- Similar to the `BasicProductsListViewModel` class  
- Inherits from `BaseObservable`  
- `notifyPropertyChanged(BR.title)`: notifies the UI/`TextView` to update  


## Item 4: ViewModel base class and binding adapters

4-1. `BaseObservable` subclasses requires lots of boilerplate code

4-2. `BaseProductListViewModel`:  
- Inherits from new `ViewModel` base class  
- `by bindable`: top-level function that uses Kotlin property delegation `BindableProperty` to encapsulate `notifyPropertyChanged()` boilerplate code.  
- `ViewModel.layoutResId`: the resource id of the layout to inflate for this view-model (more on this later)  

4-3. `MainActivity.createAndShowBaseClassViewModel()`  
- `BindingAdapters.kt`: `createView(ViewGroup, ViewModel?)`  
  - Boilerplate code for inflating a data binding layout  


## Item 5: Recycler view items and nested view-models

5-1. Nesting view-models:  
`TinyCartViewModel` / `R.layout.tiny_cart_view_model`: encapsulates the cart view with counter  

`ProductListViewModel`  
- `cartViewModel` property  
- `R.layout.product_list_view_model`:  
    ```<FrameLayout ... bind:createView="@{viewModel.cartViewModel} />"```
- `BindingAdapters.kt`:  
   - `@BindingAdapter("createView")`  
   - A binding adapter is a top-level function invoked by data binding library  

Clicking "+" button adds item to cart, counter is updated  

Rotating device, Activity destroyed/re-created but cart is maintained  

5-2. Recycler views

`ProductListViewModel.productViewModels: List<ProductSummaryViewModel>`  

`R.layout.product_list_view_model`:  
  ```<RecyclerView ... bind:recyclerViewAdapter="@{viewModel.productViewModels}" />```

`BindingAdapters.kt`:  
  ```fun bindRecyclerViewAdapter(RecyclerView, List<ViewModel>?)```

`ViewModelRecyclerViewAdapter`  
- `getItemViewType()`: uses `ViewModel.layoutResId` as unique "item type"  
- `onCreateViewHolder()`: inflates the data binding layout  
- `onBindViewHolder()`: sets `ViewModel` instance into `ViewDataBinding` variable  

5-3. Filtering product list

`ProductListViewModel`  
- `var filterText: String?`: two-way binding modifies this when `EditText` text changes  
- `onFilterTextChanged()` / `filterProducts()`: filters list of products; updates the `productViewModels` property  
- `bind:recyclerViewAdapter` binding adapter will be re-executed when `productViewModels` changes  

# Questions
If you have any questions, please email me at ssimon@esri.com

