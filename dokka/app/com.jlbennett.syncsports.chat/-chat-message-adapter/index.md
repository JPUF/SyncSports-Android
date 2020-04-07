[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatMessageAdapter](./index.md)

# ChatMessageAdapter

`class ChatMessageAdapter : Adapter<`[`ChatItemViewHolder`](../-chat-item-view-holder/index.md)`>`

The adapter class to convert the raw ChatMessages into Views to be displayed in the RecyclerView.

### Parameters

`messages` - All the received messages.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | The adapter class to convert the raw ChatMessages into Views to be displayed in the RecyclerView.`ChatMessageAdapter(messages: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatMessage`](../-chat-message/index.md)`>, listener: `[`ReplyCallback`](../-reply-callback/index.md)`)` |

### Properties

| Name | Summary |
|---|---|
| [clickListener](click-listener.md) | `var clickListener: `[`ReplyCallback`](../-reply-callback/index.md) |
| [data](data.md) | `var data: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatMessage`](../-chat-message/index.md)`>` |

### Functions

| Name | Summary |
|---|---|
| [getItemCount](get-item-count.md) | `fun getItemCount(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [indexToInsertMessage](index-to-insert-message.md) | Calculates the index within the message list that the incoming message should be inserted to.`fun indexToInsertMessage(message: `[`ChatMessage`](../-chat-message/index.md)`): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [insertMessage](insert-message.md) | Inserts the given [ChatMessage](../-chat-message/index.md) into the correct position in the list of messages.`fun insertMessage(message: `[`ChatMessage`](../-chat-message/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [isBeforeExistingMessage](is-before-existing-message.md) | Compare two MatchTime objects.`fun isBeforeExistingMessage(newMessage: `[`ChatMessage`](../-chat-message/index.md)`, existingMessage: `[`ChatMessage`](../-chat-message/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onBindViewHolder](on-bind-view-holder.md) | Populate each view with the appropriate information.`fun onBindViewHolder(holder: `[`ChatItemViewHolder`](../-chat-item-view-holder/index.md)`, position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreateViewHolder](on-create-view-holder.md) | Inflates view for each item in the list.`fun onCreateViewHolder(parent: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`, viewType: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`ChatItemViewHolder`](../-chat-item-view-holder/index.md) |
