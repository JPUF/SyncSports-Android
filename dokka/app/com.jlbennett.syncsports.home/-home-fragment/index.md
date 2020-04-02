[app](../../index.md) / [com.jlbennett.syncsports.home](../index.md) / [HomeFragment](./index.md)

# HomeFragment

`class HomeFragment : Fragment, DialogListener`

The View class for the Home screen.

This screen is responsible for username entry, colour selection, and displaying the active set of chatrooms.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | The View class for the Home screen.`HomeFragment()` |

### Properties

| Name | Summary |
|---|---|
| [binding](binding.md) | The databinding object. It contains references to all View objects in this Fragment's layout XML.`lateinit var binding: <ERROR CLASS>` |
| [color](color.md) | `var color: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [roomAdapter](room-adapter.md) | `lateinit var roomAdapter: `[`RoomAdapter`](../-room-adapter/index.md) |
| [sharedPref](shared-pref.md) | Reference to basic local storage, for username and preferred colour.`lateinit var sharedPref: `[`SharedPreferences`](https://developer.android.com/reference/android/content/SharedPreferences.html) |
| [username](username.md) | `lateinit var username: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [viewModel](view-model.md) | `lateinit var viewModel: `[`HomeViewModel`](../-home-view-model/index.md) |

### Functions

| Name | Summary |
|---|---|
| [checkUsername](check-username.md) | Calls [isValidUsername](is-valid-username.md) to determine validity. Updates the UI to give feedback to user.`fun checkUsername(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [isValidUsername](is-valid-username.md) | Tests if the username is appropriate for use.`fun isValidUsername(username: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onColorSelected](on-color-selected.md) | The callback function for the colour selection dialog.`fun onColorSelected(colorID: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreateView](on-create-view.md) | `fun onCreateView(inflater: `[`LayoutInflater`](https://developer.android.com/reference/android/view/LayoutInflater.html)`, container: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`?, savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`View`](https://developer.android.com/reference/android/view/View.html)`?` |
| [onPause](on-pause.md) | `fun onPause(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onResume](on-resume.md) | `fun onResume(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setRoomHeader](set-room-header.md) | Updates the TextView above the list of rooms.`fun setRoomHeader(stringID: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [storeColor](store-color.md) | Stores a given Android colour ID integer into the phones local storage via SharedPreferences. Under tag "color"`fun storeColor(colorID: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [storeName](store-name.md) | Stores a given name into sharedPrefences. Under tag "username"`fun storeName(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [storeUser](store-user.md) | A convenience function that calls [storeColor](store-color.md) and [storeUser](store-user.md)`fun storeUser(user: `[`User`](../../com.jlbennett.syncsports.util/-user/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
