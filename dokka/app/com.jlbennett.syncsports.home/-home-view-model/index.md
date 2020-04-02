[app](../../index.md) / [com.jlbennett.syncsports.home](../index.md) / [HomeViewModel](./index.md)

# HomeViewModel

`class HomeViewModel : AndroidViewModel`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `HomeViewModel(application: `[`Application`](https://developer.android.com/reference/android/app/Application.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [_eventRoomListPopulated](_event-room-list-populated.md) | `val _eventRoomListPopulated: MutableLiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [_roomList](_room-list.md) | `val _roomList: MutableLiveData<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Chatroom`](../../com.jlbennett.syncsports.util/-chatroom/index.md)`>>` |
| [eventRoomListPopulated](event-room-list-populated.md) | `val eventRoomListPopulated: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [roomList](room-list.md) | `val roomList: LiveData<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Chatroom`](../../com.jlbennett.syncsports.util/-chatroom/index.md)`>>` |
| [roomSocket](room-socket.md) | `val roomSocket: Socket!` |

### Functions

| Name | Summary |
|---|---|
| [connectToRoomAPI](connect-to-room-a-p-i.md) | `fun connectToRoomAPI(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [disconnectFromRooms](disconnect-from-rooms.md) | `fun disconnectFromRooms(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getRoomCount](get-room-count.md) | `fun getRoomCount(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [onDisplayRoomsComplete](on-display-rooms-complete.md) | `fun onDisplayRoomsComplete(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [reconnectToRooms](reconnect-to-rooms.md) | `fun reconnectToRooms(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
