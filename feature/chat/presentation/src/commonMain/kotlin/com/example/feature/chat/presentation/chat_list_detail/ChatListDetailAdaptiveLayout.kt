@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.example.feature.chat.presentation.chat_list_detail

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.extended
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatListDetailAdaptiveLayout(
    viewModel: ChatListDetailViewModel = koinViewModel<ChatListDetailViewModel>()
) {
    val paneScaffoldDirective = createPaneScaffoldDirectiveByConfigurationAndAdaptiveInfo()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(
        scaffoldDirective = paneScaffoldDirective
    )

    ListDetailPaneScaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.extended.surfaceLower),
        directive = paneScaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        listPane = {
            // TODO: implement list pane
        },
        detailPane = {
            // TODO: implement detail list pane
        },
    )
}