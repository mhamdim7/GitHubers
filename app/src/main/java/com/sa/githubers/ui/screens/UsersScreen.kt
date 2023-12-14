package com.sa.githubers.ui.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.ui.components.EmptyStateComponent
import com.sa.githubers.ui.components.ErrorComponent
import com.sa.githubers.ui.components.InfinitelyFlowingCircles
import com.sa.githubers.ui.components.ProgressLoader
import com.sa.githubers.ui.components.SearchFieldComponent
import com.sa.githubers.ui.components.UsersList
import com.sa.githubers.ui.viewmodel.UsersViewModel

@Composable
fun UsersScreen(
    navigateToDetails: (login: String) -> Unit,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val searchText by viewModel.searchText.collectAsState()
    val usersResponse by viewModel.users.collectAsState()
    val focusManager = LocalFocusManager.current

    Surface(modifier = Modifier.fillMaxSize(), color = colorScheme.background) {
        Column(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }) {
            SearchFieldComponent(
                searchText = searchText,
                onValueChange = { viewModel.onSearchTextChange(it) })
            when (val users = usersResponse) {

                is ResourceState.Idle -> InfinitelyFlowingCircles()

                is ResourceState.Loading -> ProgressLoader()

                is ResourceState.Success ->
                    if (users.data.isEmpty()) EmptyStateComponent()
                    else UsersList(users.data) { navigateToDetails(it) }

                is ResourceState.Error -> {
                    ErrorComponent(users.message)
                }
            }
        }
    }
}