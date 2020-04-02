[app](../index.md) / [com.jlbennett.syncsports.chat](./index.md)

## Package com.jlbennett.syncsports.chat

### Types

| Name | Summary |
|---|---|
| [ChatFragment](-chat-fragment/index.md) | The View logic for the Chat screen. Handles UI logic.`class ChatFragment : Fragment, DialogListener` |
| [ChatItemViewHolder](-chat-item-view-holder/index.md) | `class ChatItemViewHolder : ViewHolder` |
| [ChatMessage](-chat-message/index.md) | A Plain-Old-Data class to represent a single message.`data class ChatMessage` |
| [ChatMessageAdapter](-chat-message-adapter/index.md) | The adapter class to convert the raw ChatMessages into Views to be displayed in the RecyclerView.`class ChatMessageAdapter : Adapter<`[`ChatItemViewHolder`](-chat-item-view-holder/index.md)`>` |
| [ChatViewModel](-chat-view-model/index.md) | The ViewModel for the ChatFragment. This handles all the dynamic data shown in the ChatFragment.`class ChatViewModel : ViewModel` |
| [ChatViewModelFactory](-chat-view-model-factory/index.md) | A class to create a ChatViewModel with the given parameters.`class ChatViewModelFactory : Factory` |
| [TimeAdjustDialogFragment](-time-adjust-dialog-fragment/index.md) | The logic to allow the user to adjust their current [MatchTime](../com.jlbennett.syncsports.util/-match-time/index.md) without having to exit the chatroom.`class TimeAdjustDialogFragment : DialogFragment` |
