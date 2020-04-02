[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [TimeAdjustDialogFragment](./index.md)

# TimeAdjustDialogFragment

`class TimeAdjustDialogFragment : DialogFragment`

The logic to allow the user to adjust their current [MatchTime](../../com.jlbennett.syncsports.util/-match-time/index.md) without having to exit the chatroom.

### Types

| Name | Summary |
|---|---|
| [DialogListener](-dialog-listener/index.md) | `interface DialogListener` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | The logic to allow the user to adjust their current [MatchTime](../../com.jlbennett.syncsports.util/-match-time/index.md) without having to exit the chatroom.`TimeAdjustDialogFragment()` |

### Properties

| Name | Summary |
|---|---|
| [adjustedTime](adjusted-time.md) | `var adjustedTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md) |
| [adjustmentSeconds](adjustment-seconds.md) | `var adjustmentSeconds: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [currentTime](current-time.md) | `var currentTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md) |
| [dialogIsShown](dialog-is-shown.md) | Reflects the state of whether or not this dialog is currently visible.`var dialogIsShown: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [newTimeText](new-time-text.md) | `lateinit var newTimeText: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html) |
| [timeIsUpdated](time-is-updated.md) | Reflects whether or not the user has updated their time.`var timeIsUpdated: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| Name | Summary |
|---|---|
| [onCreateView](on-create-view.md) | Sets the onClickListeners for each button.`fun onCreateView(inflater: `[`LayoutInflater`](https://developer.android.com/reference/android/view/LayoutInflater.html)`, container: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`?, savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`View`](https://developer.android.com/reference/android/view/View.html)`?` |
| [onDestroyView](on-destroy-view.md) | `fun onDestroyView(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setAdjustment](set-adjustment.md) | Increments or decrements the change in seconds by the appropriate amount for each button.`fun setAdjustment(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [timeSet](time-set.md) | `fun timeSet(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [updateTime](update-time.md) | `fun updateTime(matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`? = null): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
