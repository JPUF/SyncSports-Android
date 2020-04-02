[app](../../index.md) / [com.jlbennett.syncsports.home](../index.md) / [HomeViewModel](./index.md)

# HomeViewModel

`class HomeViewModel : AndroidViewModel`

ViewModel for the HomeFragment.

This fragment is exposed via the ViewModelProvider class. It's a singleton, and survives Android configuration changes.

### Parameters

`application` - The android application context.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | ViewModel for the HomeFragment.`HomeViewModel(application: `[`Application`](https://developer.android.com/reference/android/app/Application.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [_eventRoomListPopulated](_event-room-list-populated.md) | The private and mutable boolean that represents whether the roomList has been populated with rooms.`val _eventRoomListPopulated: MutableLiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [_roomList](_room-list.md) | The private and mutable list of Chatrooms.`val _roomList: MutableLiveData<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Chatroom`](../../com.jlbennett.syncsports.util/-chatroom/index.md)`>>` |
| [eventRoomListPopulated](event-room-list-populated.md) | The public and immutable boolean. The Fragment observes changes in this variable to know when to read the roomList.`val eventRoomListPopulated: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [roomList](room-list.md) | The public and immutable list of Chatrooms. This exposes the private list of Chatrooms to the Fragment.`val roomList: LiveData<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Chatroom`](../../com.jlbennett.syncsports.util/-chatroom/index.md)`>>` |
| [roomSocket](room-socket.md) | Reference to the socket server 'rooms' namespace. Note: http not https.`val roomSocket: Socket!` |

### Functions

| Name | Summary |
|---|---|
| [connectToRoomAPI](connect-to-room-a-p-i.md) | Assigns callback functions to be executed on socket events.`fun connectToRoomAPI(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [disconnectFromRooms](disconnect-from-rooms.md) | Disconnect from the room namespace.`fun disconnectFromRooms(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getRoomCount](get-room-count.md) | The current number of rooms.`fun getRoomCount(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [onDisplayRoomsComplete](on-display-rooms-complete.md) | Reset the boolean, so future changes can be observed.`fun onDisplayRoomsComplete(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [reconnectToRooms](reconnect-to-rooms.md) | Reconnect to the room namespace if currently disconnected.`fun reconnectToRooms(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
