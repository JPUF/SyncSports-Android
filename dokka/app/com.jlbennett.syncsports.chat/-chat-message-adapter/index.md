[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatMessageAdapter](./index.md)

# ChatMessageAdapter

`class ChatMessageAdapter : Adapter<`[`ChatItemViewHolder`](../-chat-item-view-holder/index.md)`>`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ChatMessageAdapter(messages: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatMessage`](../-chat-message/index.md)`>)` |

### Properties

| Name | Summary |
|---|---|
| [data](data.md) | `var data: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatMessage`](../-chat-message/index.md)`>` |

### Functions

| Name | Summary |
|---|---|
| [addMessage](add-message.md) | `fun addMessage(message: `[`ChatMessage`](../-chat-message/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getItemCount](get-item-count.md) | `fun getItemCount(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [onBindViewHolder](on-bind-view-holder.md) | `fun onBindViewHolder(holder: `[`ChatItemViewHolder`](../-chat-item-view-holder/index.md)`, position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreateViewHolder](on-create-view-holder.md) | `fun onCreateViewHolder(parent: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`, viewType: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`ChatItemViewHolder`](../-chat-item-view-holder/index.md) |
