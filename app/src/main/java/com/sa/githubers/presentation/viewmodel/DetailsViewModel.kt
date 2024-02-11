package com.sa.githubers.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.domain.usecases.UserDetailUseCase
import com.sa.githubers.presentation.mapper.UserDetailsDomainToUiMapper
import com.sa.githubers.presentation.mapper.UserRepoDomainToUiMapper
import com.sa.githubers.presentation.model.UserDetailsUiModel
import com.sa.githubers.presentation.model.UserRepoUiModel
import com.sa.githubers.presentation.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: UserDetailUseCase,
    private val userDetailsMapper: UserDetailsDomainToUiMapper,
    private val userRepoMapper: UserRepoDomainToUiMapper
) : ViewModel() {


    private val _userDetails = MutableStateFlow<ResourceState<UserDetailsUiModel>>(
        ResourceState.Idle()
    )
    val userDetails = _userDetails.asStateFlow()

    private val _repos = MutableStateFlow<ResourceState<List<UserRepoUiModel>>>(
        ResourceState.Idle()
    )
    val repos = _repos.asStateFlow()

    init {
        val userLogin = savedStateHandle[Routes.DETAILS_SCREEN_KEY] ?: ""
        fetchUserDetails(userLogin)
        fetchUserRepos(userLogin)
    }

    private fun fetchUserDetails(userLogin: String) = viewModelScope.launch(Dispatchers.IO) {
        useCase.getUserProfile(userLogin).collectLatest {
            _userDetails.value = userDetailsMapper.mapFrom(it)
        }
    }

    private fun fetchUserRepos(userLogin: String) = viewModelScope.launch(Dispatchers.IO) {
        useCase.getUserRepos(userLogin).collectLatest {
            _repos.value = userRepoMapper.mapFrom(it)
        }
    }
}