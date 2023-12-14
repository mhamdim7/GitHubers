package com.sa.githubers.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sa.githubers.data.entity.ProfileResponse
import com.sa.githubers.data.entity.RepoEntry
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.domain.usecases.UserDetailUseCase
import com.sa.githubers.ui.RepoItemUiModel
import com.sa.githubers.ui.UserDetailsUiModel
import com.sa.githubers.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val useCase: UserDetailUseCase
) : ViewModel() {


    private val _userDetails =
        MutableStateFlow<ResourceState<UserDetailsUiModel>>(ResourceState.Idle())
    val userDetails: StateFlow<ResourceState<UserDetailsUiModel>> = _userDetails

    private val _repos =
        MutableStateFlow<ResourceState<List<RepoItemUiModel>>>(ResourceState.Idle())
    val repos: StateFlow<ResourceState<List<RepoItemUiModel>>> = _repos

    init {
        val userLogin = savedStateHandle[Routes.DETAILS_SCREEN_KEY] ?: ""
        fetchUserDetails(userLogin)
        fetchUserRepos(userLogin)
    }

    private fun fetchUserDetails(userLogin: String) = viewModelScope.launch(Dispatchers.IO) {
        useCase.getUserProfile(userLogin).collectLatest { resourceState ->
            _userDetails.value = resourceState.mapSuccess {
                toUserDetailsUiModel(it)
            }
        }
    }

    private fun fetchUserRepos(userLogin: String) = viewModelScope.launch(Dispatchers.IO) {
        useCase.getUserRepos(userLogin).collectLatest { resourceState ->
            _repos.value = resourceState.mapSuccess { repoList ->
                repoList.map { toRepoUiModel(it) }
            }
        }
    }

    // could be in a Mapper class
    private fun toUserDetailsUiModel(it: ProfileResponse) =
        UserDetailsUiModel(
            it.login,
            it.id,
            it.avatarUrl,
            it.location,
            it.name,
            it.bio,
            it.hireable ?: false
        )

    private fun toRepoUiModel(repo: RepoEntry) =
        RepoItemUiModel(
            repo.id,
            repo.name,
            repo.private,
            repo.description,
        )
}