[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatViewModelFactory](./index.md)

# ChatViewModelFactory

`class ChatViewModelFactory : Factory`

A class to create a ChatViewModel with the given parameters.

This is used to return the singleton ViewModel class from within the ChatFragment class.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | A class to create a ChatViewModel with the given parameters.`ChatViewModelFactory(matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`, roomName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, user: `[`User`](../../com.jlbennett.syncsports.util/-user/index.md)`)` |

### Properties

| Name | Summary |
|---|---|
| [matchTime](match-time.md) | `val matchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md) |
| [roomName](room-name.md) | `val roomName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [user](user.md) | `val user: `[`User`](../../com.jlbennett.syncsports.util/-user/index.md) |

### Functions

| Name | Summary |
|---|---|
| [create](create.md) | `fun <T : ViewModel?> create(modelClass: `[`Class`](https://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>): T` |
