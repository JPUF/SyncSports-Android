[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatViewModel](./index.md)

# ChatViewModel

`class ChatViewModel : ViewModel`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ChatViewModel(matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`, roomName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, user: `[`User`](../../com.jlbennett.syncsports.util/-user/index.md)`)` |

### Properties

| Name | Summary |
|---|---|
| [eventMessageToShow](event-message-to-show.md) | `val eventMessageToShow: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [matchTime](match-time.md) | `val matchTime: LiveData<`[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`>` |
| [messages](messages.md) | `val messages: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatMessage`](../-chat-message/index.md)`>` |
| [receivedMessage](received-message.md) | `val receivedMessage: LiveData<`[`ChatMessage`](../-chat-message/index.md)`>` |

### Functions

| Name | Summary |
|---|---|
| [disconnectFromChatroom](disconnect-from-chatroom.md) | `fun disconnectFromChatroom(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDisplayMessageComplete](on-display-message-complete.md) | `fun onDisplayMessageComplete(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [pauseTimer](pause-timer.md) | `fun pauseTimer(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [reconnectToChatroom](reconnect-to-chatroom.md) | `fun reconnectToChatroom(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [resumeTimer](resume-timer.md) | `fun resumeTimer(matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [sendMessage](send-message.md) | `fun sendMessage(message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [updateMatchTime](update-match-time.md) | `fun updateMatchTime(matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
