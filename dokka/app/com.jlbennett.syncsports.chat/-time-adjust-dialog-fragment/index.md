[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [TimeAdjustDialogFragment](./index.md)

# TimeAdjustDialogFragment

`class TimeAdjustDialogFragment : DialogFragment`

### Types

| Name | Summary |
|---|---|
| [DialogListener](-dialog-listener/index.md) | `interface DialogListener` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `TimeAdjustDialogFragment()` |

### Properties

| Name | Summary |
|---|---|
| [adjustedTime](adjusted-time.md) | `var adjustedTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md) |
| [adjustmentSeconds](adjustment-seconds.md) | `var adjustmentSeconds: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [currentTime](current-time.md) | `var currentTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md) |
| [dialogIsShown](dialog-is-shown.md) | `var dialogIsShown: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [newTimeText](new-time-text.md) | `lateinit var newTimeText: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html) |
| [timeIsUpdated](time-is-updated.md) | `var timeIsUpdated: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| Name | Summary |
|---|---|
| [onCreateView](on-create-view.md) | `fun onCreateView(inflater: `[`LayoutInflater`](https://developer.android.com/reference/android/view/LayoutInflater.html)`, container: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`?, savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`View`](https://developer.android.com/reference/android/view/View.html)`?` |
| [onDestroyView](on-destroy-view.md) | `fun onDestroyView(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setAdjustment](set-adjustment.md) | `fun setAdjustment(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [timeSet](time-set.md) | `fun timeSet(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [updateTime](update-time.md) | `fun updateTime(matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`? = null): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
