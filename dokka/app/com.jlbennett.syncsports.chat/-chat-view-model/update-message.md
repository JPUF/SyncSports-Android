[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatViewModel](index.md) / [updateMessage](./update-message.md)

# updateMessage

`private fun updateMessage(chatMessage: `[`ChatMessage`](../-chat-message/index.md)`, delay: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Creates a new [Runnable](https://docs.oracle.com/javase/6/docs/api/java/lang/Runnable.html) object which updates the LiveData fields with the message to be shown.

### Parameters

`chatMessage` - The message to be shown.

`delay` - The number of milliseconds to wait before that message should be shown.