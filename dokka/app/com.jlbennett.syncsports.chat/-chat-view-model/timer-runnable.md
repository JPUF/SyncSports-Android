[app](../../index.md) / [com.jlbennett.syncsports.chat](../index.md) / [ChatViewModel](index.md) / [timerRunnable](./timer-runnable.md)

# timerRunnable

`private val timerRunnable: `[`Runnable`](https://docs.oracle.com/javase/6/docs/api/java/lang/Runnable.html)

A runnable object to be executed via the Handler. It updates the 'clock'.

It is updated every 1/4 second. This gives a theoretical message timing accuracy of +/-0.25s.

