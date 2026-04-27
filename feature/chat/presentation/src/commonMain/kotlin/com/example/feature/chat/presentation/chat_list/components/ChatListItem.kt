@file:OptIn(ExperimentalUuidApi::class)

package com.example.feature.chat.presentation.chat_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import chirpappkmp.feature.chat.presentation.generated.resources.Res
import chirpappkmp.feature.chat.presentation.generated.resources.group_chat
import com.example.core.designsystem.components.avatar.ChirpStackedAvatars
import com.example.core.designsystem.theme.ChirpTheme
import com.example.core.designsystem.theme.extended
import com.example.core.designsystem.theme.titleXSmall
import com.example.feature.chat.presentation.model.ChatUi
import com.example.feature.chat.presentation.type_alias.ChatParticipantUi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun ChatListItem(
    chatUi: ChatUi,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) = with(chatUi) {
    val selectedColor =
        if (isSelected) MaterialTheme.colorScheme.surface
        else MaterialTheme.colorScheme.extended.surfaceLower
    val selectedAlpha = if (isSelected) 1f else 0f
    val isGroupChat = otherParticipants.size > 1
    val chatName = if (isGroupChat) stringResource(Res.string.group_chat)
    else otherParticipants.first().username
    val formattedUsernames = remember(otherParticipants) {
        otherParticipants.joinToString { it.username }
    }

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .background(selectedColor)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ChirpStackedAvatars(
                    avatars = otherParticipants
                )
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = chatName,
                        style = MaterialTheme.typography.titleXSmall,
                        color = MaterialTheme.colorScheme.extended.textPrimary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    if (isGroupChat) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = formattedUsernames,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.extended.textPlaceholder,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
            // TODO: text message
        }
        Box(
            modifier = Modifier
                .alpha(selectedAlpha)
                .background(MaterialTheme.colorScheme.primary)
                .width(4.dp)
                .fillMaxHeight()
        )
    }
}

@Preview
@Composable
fun PreviewChatListItem() {
    ChirpTheme {
        ChatListItem(
            chatUi = ChatUi(
                id = Uuid.random().toString(),
                localParticipant = ChatParticipantUi(
                    id = Uuid.random().toString(),
                    username = "Lorem ipsum 1",
                    initials = "LI1"
                ),
                otherParticipants = listOf(
                    ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum 1",
                        initials = "LI1"
                    )
                ),
                lastMessage = null
            ),
            isSelected = false,
        )
    }
}

@Preview
@Composable
fun PreviewDarkChatListItem() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChatListItem(
            chatUi = ChatUi(
                id = Uuid.random().toString(),
                localParticipant = ChatParticipantUi(
                    id = Uuid.random().toString(),
                    username = "Lorem ipsum 1",
                    initials = "LI1"
                ),
                otherParticipants = listOf(
                    ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum 1",
                        initials = "LI1"
                    )
                ),
                lastMessage = null
            ),
            isSelected = false,
        )
    }
}

@Preview
@Composable
fun PreviewChatListItemGroup() {
    ChirpTheme {
        ChatListItem(
            chatUi = ChatUi(
                id = Uuid.random().toString(),
                localParticipant = ChatParticipantUi(
                    id = Uuid.random().toString(),
                    username = "Lorem ipsum 1",
                    initials = "LI1"
                ),
                otherParticipants = listOf(
                    ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum 2",
                        initials = "LI2"
                    ),
                    ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum 3",
                        initials = "LI3"
                    )
                ),
                lastMessage = null
            ),
            isSelected = false,
        )
    }
}

@Preview
@Composable
fun PreviewDarkChatListItemGroup() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChatListItem(
            chatUi = ChatUi(
                id = Uuid.random().toString(),
                localParticipant = ChatParticipantUi(
                    id = Uuid.random().toString(),
                    username = "Lorem ipsum 1",
                    initials = "LI1"
                ),
                otherParticipants = listOf(
                    ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum 2",
                        initials = "LI2"
                    ),
                    ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum 3",
                        initials = "LI3"
                    )
                ),
                lastMessage = null
            ),
            isSelected = false,
        )
    }
}