package com.sa.githubers.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.ui.components.DetailsHeader
import com.sa.githubers.ui.components.ErrorComponent
import com.sa.githubers.ui.components.ProgressLoader
import com.sa.githubers.ui.components.ReposList
import com.sa.githubers.ui.components.ReposTitle
import com.sa.githubers.ui.theme.dimens
import com.sa.githubers.ui.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val userDetails by viewModel.userDetails.collectAsState()
    val repoList by viewModel.repos.collectAsState()
    Surface(modifier = Modifier.fillMaxSize(), color = colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            when (val details = userDetails) {
                is ResourceState.Idle -> {
                    // no-op
                }

                is ResourceState.Loading -> ProgressLoader()

                is ResourceState.Success -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(MaterialTheme.dimens.medium)
                            .fillMaxWidth()
                    ) {
                        DetailsHeader(details.data)
                        ReposTitle()
                        when (val repos = repoList) {
                            is ResourceState.Idle -> {
                                // no-op
                            }

                            is ResourceState.Loading -> ProgressLoader()
                            is ResourceState.Success -> {
                                val items = repos.data
                                ReposList(items)
                            }

                            is ResourceState.Error -> ErrorComponent(message = repos.message)
                        }

                    }

                }

                is ResourceState.Error -> ErrorComponent(message = details.message)
            }

        }
    }
}
