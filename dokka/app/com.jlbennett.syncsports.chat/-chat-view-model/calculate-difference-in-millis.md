[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatViewModel](index.md) / [calculateDifferenceInMillis](./calculate-difference-in-millis.md)

# calculateDifferenceInMillis

`private fun calculateDifferenceInMillis(incomingMatchTime: `[`MatchTime`](../../com.jlbennett.syncsports.util/-match-time/index.md)`): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)

Calculates the difference in [MatchTime](../../com.jlbennett.syncsports.util/-match-time/index.md) between the received message and the current user.

**Return**
The difference in milliseconds between the received message and the current user's match time. Unless the current user is ahead in the match, then it returns 0.

