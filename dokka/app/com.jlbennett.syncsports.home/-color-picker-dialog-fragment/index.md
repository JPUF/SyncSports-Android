[app](../../index.md) / [com.jlbennett.syncsports.home](../index.md) / [ColorPickerDialogFragment](./index.md)

# ColorPickerDialogFragment

`class ColorPickerDialogFragment : DialogFragment`

The logic for the ColorPicker pop-up.

As a DialogFragment, it appears above the underlying HomeFragment.

### Types

| Name | Summary |
|---|---|
| [DialogListener](-dialog-listener/index.md) | Implementing this allows for communication between the DialogFragment and the HomeFragment.`interface DialogListener` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | The logic for the ColorPicker pop-up.`ColorPickerDialogFragment()` |

### Functions

| Name | Summary |
|---|---|
| [onCreateView](on-create-view.md) | This inflates the view, and sets the onClickListener for each button within the DialogFragment.`fun onCreateView(inflater: `[`LayoutInflater`](https://developer.android.com/reference/android/view/LayoutInflater.html)`, container: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`?, savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`View`](https://developer.android.com/reference/android/view/View.html)`?` |
| [onDestroy](on-destroy.md) | `fun onDestroy(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setColor](set-color.md) | Send the chosen colour to the assigned listener.`fun setColor(button: `[`View`](https://developer.android.com/reference/android/view/View.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
