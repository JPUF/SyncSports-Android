[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatMessageAdapter](index.md) / [insertMessage](./insert-message.md)

# insertMessage

`fun insertMessage(message: `[`ChatMessage`](../-chat-message/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Inserts the given [ChatMessage](../-chat-message/index.md) into the correct position in the list of messages.

This position would typically be at the bottom of the list. However, because of asynchronous communication, the message may need to be inserted before more recent messages.

