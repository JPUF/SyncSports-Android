[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatFragment](./index.md)

# ChatFragment

`class ChatFragment : Fragment, DialogListener`

The View logic for the Chat screen. Handles UI logic.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | The View logic for the Chat screen. Handles UI logic.`ChatFragment()` |

### Properties

| Name | Summary |
|---|---|
| [recyclerViewAdapter](recycler-view-adapter.md) | `lateinit var recyclerViewAdapter: `[`ChatMessageAdapter`](../-chat-message-adapter/index.md) |
| [sharedPref](shared-pref.md) | Used for reading from local storage.`lateinit var sharedPref: `[`SharedPreferences`](https://developer.android.com/reference/android/content/SharedPreferences.html) |
| [user](user.md) | The current user. Includes their name and colour.`lateinit var user: `[`User`](../../com.jlbennett.syncsports.util/-user/index.md) |
| [viewModel](view-model.md) | Reference to the ViewModel singleton. This handles the fragment's dynamic data, including message i/o.`lateinit var viewModel: `[`ChatViewModel`](../-chat-view-model/index.md) |
| [viewModelFactory](view-model-factory.md) | The a factory class, to allow for a ViewModel with a constructor parameters.`lateinit var viewModelFactory: `[`ChatViewModelFactory`](../-chat-view-model-factory/index.md) |

### Functions

| Name | Summary |
|---|---|
| [displayMessage](display-message.md) | Adds a message to the adapter.`fun displayMessage(chatMessage: `[`ChatMessage`](../-chat-message/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreateView](on-create-view.md) | `fun onCreateView(inflater: `[`LayoutInflater`](https://developer.android.com/reference/android/view/LayoutInflater.html)`, container: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`?, savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`View`](https://developer.android.com/reference/android/view/View.html)`?` |
| [onPause](on-pause.md) | Handle the user navigating away from the chat screen.`fun onPause(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onResume](on-resume.md) | Handle user returning to the chat screen after having navigated away.`fun onResume(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onTimeSet](on-time-set.md) | Callback function for an updated time.`fun onTimeSet(matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [readUser](read-user.md) | Reads the locally stored user object`fun readUser(): `[`User`](../../com.jlbennett.syncsports.util/-user/index.md) |
