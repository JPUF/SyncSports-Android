[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatViewModel](./index.md)

# ChatViewModel

`class ChatViewModel : ViewModel`

The ViewModel for the ChatFragment. This handles all the dynamic data shown in the ChatFragment.

This class's responsibilities include handling the time. Sending/Receiving messages, and the delay algorithm.

### Parameters

`matchTime` - The initial match time, on the ViewModel construction.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | The ViewModel for the ChatFragment. This handles all the dynamic data shown in the ChatFragment.`ChatViewModel(matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`, roomName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, user: `[`User`](../../com.jlbennett.syncsports.util/-user/index.md)`)` |

### Properties

| Name | Summary |
|---|---|
| [_eventMessageToShow](_event-message-to-show.md) | A private and mutable LiveData field, it represents whether or not there is a new message to be shown in the UI.`val _eventMessageToShow: MutableLiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [_matchTime](_match-time.md) | The private current match time, which is updated from within this class.`val _matchTime: MutableLiveData<`[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`>` |
| [_receivedMessage](_received-message.md) | Private and mutable field that stores the most recently received message.`val _receivedMessage: MutableLiveData<`[`ChatMessage`](../-chat-message/index.md)`>` |
| [_user](_user.md) | `var _user: `[`User`](../../com.jlbennett.syncsports.util/-user/index.md) |
| [chatSocket](chat-socket.md) | Reference to the chat namespace on the server.`val chatSocket: Socket!` |
| [eventMessageToShow](event-message-to-show.md) | The public LiveData which exposes the privately Mutable field. Represents whether or not there's a new message to be shown.`val eventMessageToShow: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [handler](handler.md) | The Android Handler that coordinates execution of the timer updating.`val handler: `[`Handler`](https://developer.android.com/reference/android/os/Handler.html) |
| [matchTime](match-time.md) | Exposes the current match time immutably to the Fragment, to be shown in the UI.`val matchTime: LiveData<`[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`>` |
| [messages](messages.md) | The current messages.`val messages: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatMessage`](../-chat-message/index.md)`>` |
| [receivedMessage](received-message.md) | Exposes the most recently received message in an immutable fashion.`val receivedMessage: LiveData<`[`ChatMessage`](../-chat-message/index.md)`>` |
| [room](room.md) | `val room: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [timerRunnable](timer-runnable.md) | A runnable object to be executed via the Handler. It updates the 'clock'.`val timerRunnable: `[`Runnable`](https://docs.oracle.com/javase/6/docs/api/java/lang/Runnable.html) |

### Functions

| Name | Summary |
|---|---|
| [calculateDifferenceInMillis](calculate-difference-in-millis.md) | Calculates the difference in [MatchTime](../../com.jlbennett.syncsports.util/-match-time/index.md) between the received message and the current user.`fun calculateDifferenceInMillis(incomingMatchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [connectToChatAPI](connect-to-chat-a-p-i.md) | Connects to the chat socket namespace. Registers callback functions.`fun connectToChatAPI(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [disconnectFromChatroom](disconnect-from-chatroom.md) | `fun disconnectFromChatroom(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDisplayMessageComplete](on-display-message-complete.md) | Reset the private _eventMessageToShow to false, so that we can observe future new messages.`fun onDisplayMessageComplete(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [pauseTimer](pause-timer.md) | `fun pauseTimer(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [reconnectToChatroom](reconnect-to-chatroom.md) | `fun reconnectToChatroom(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [resumeTimer](resume-timer.md) | `fun resumeTimer(matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [sendMessage](send-message.md) | Sends a message to the current room.`fun sendMessage(message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [updateMatchTime](update-match-time.md) | `fun updateMatchTime(matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [updateMessage](update-message.md) | Creates a new [Runnable](https://docs.oracle.com/javase/6/docs/api/java/lang/Runnable.html) object which updates the LiveData fields with the message to be shown.`fun updateMessage(chatMessage: `[`ChatMessage`](../-chat-message/index.md)`, delay: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
