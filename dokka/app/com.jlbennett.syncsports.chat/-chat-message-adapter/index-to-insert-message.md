[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatMessageAdapter](index.md) / [indexToInsertMessage](./index-to-insert-message.md)

# indexToInsertMessage

`private fun indexToInsertMessage(message: `[`ChatMessage`](../-chat-message/index.md)`): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)

Calculates the index within the message list that the incoming message should be inserted to.

**Return**
Either the calculated index, or -1 if the message should instead simply be appended to the end of the existing list.

