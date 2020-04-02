[app](../../index.md) / [com.jlbennett.syncsports.home](../index.md) / [RoomAdapter](./index.md)

# RoomAdapter

`class RoomAdapter : Adapter<`[`RoomViewHolder`](../-room-view-holder/index.md)`>`

The adapter class for the RecyclerView displaying all rooms.

It converts the details of all chatrooms to the layout specified in room_item.xml

### Parameters

`rooms` - The details of all current chatrooms.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | The adapter class for the RecyclerView displaying all rooms.`RoomAdapter(rooms: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Chatroom`](../../com.jlbennett.syncsports.util/-chatroom/index.md)`>)` |

### Properties

| Name | Summary |
|---|---|
| [roomList](room-list.md) | `val roomList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Chatroom`](../../com.jlbennett.syncsports.util/-chatroom/index.md)`>` |

### Functions

| Name | Summary |
|---|---|
| [getItemCount](get-item-count.md) | `fun getItemCount(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [onBindViewHolder](on-bind-view-holder.md) | Sets the layout View objects to the appropriate information.`fun onBindViewHolder(holder: `[`RoomViewHolder`](../-room-view-holder/index.md)`, position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreateViewHolder](on-create-view-holder.md) | The ViewHolder is a wrapper around each room_item view. This facilitates the 'recycling'.`fun onCreateViewHolder(parent: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`, viewType: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`RoomViewHolder`](../-room-view-holder/index.md) |
