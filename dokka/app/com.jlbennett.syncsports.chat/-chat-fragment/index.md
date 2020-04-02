[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatFragment](./index.md)

# ChatFragment

`class ChatFragment : Fragment, DialogListener`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ChatFragment()` |

### Properties

| Name | Summary |
|---|---|
| [recyclerViewAdapter](recycler-view-adapter.md) | `lateinit var recyclerViewAdapter: `[`ChatMessageAdapter`](../-chat-message-adapter/index.md) |
| [sharedPref](shared-pref.md) | `lateinit var sharedPref: `[`SharedPreferences`](https://developer.android.com/reference/android/content/SharedPreferences.html) |
| [user](user.md) | `lateinit var user: `[`User`](../../com.jlbennett.syncsports.util/-user/index.md) |
| [viewModel](view-model.md) | `lateinit var viewModel: `[`ChatViewModel`](../-chat-view-model/index.md) |
| [viewModelFactory](view-model-factory.md) | `lateinit var viewModelFactory: `[`ChatViewModelFactory`](../-chat-view-model-factory/index.md) |

### Functions

| Name | Summary |
|---|---|
| [displayMessage](display-message.md) | `fun displayMessage(chatMessage: `[`ChatMessage`](../-chat-message/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreateView](on-create-view.md) | `fun onCreateView(inflater: `[`LayoutInflater`](https://developer.android.com/reference/android/view/LayoutInflater.html)`, container: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`?, savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`View`](https://developer.android.com/reference/android/view/View.html)`?` |
| [onPause](on-pause.md) | `fun onPause(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onResume](on-resume.md) | `fun onResume(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onTimeSet](on-time-set.md) | `fun onTimeSet(matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [readUser](read-user.md) | `fun readUser(): `[`User`](../../com.jlbennett.syncsports.util/-user/index.md) |
