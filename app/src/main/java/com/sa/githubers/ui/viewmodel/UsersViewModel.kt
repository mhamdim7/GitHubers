package com.sa.githubers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sa.githubers.AppConstants.THROTTLE_DURATION
import com.sa.githubers.data.entity.UserEntry
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.domain.usecases.UsersUseCase
import com.sa.githubers.ui.UserItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val usersUseCase: UsersUseCase) : ViewModel() {

    private val _users = MutableStateFlow<ResourceState<List<UserItemUiModel>>>(
        ResourceState.Idle()
    )
    private val _searchText = MutableStateFlow("")
    val users = _users.asStateFlow()
    val searchText = _searchText.asStateFlow()

    fun onSearchTextChange(query: String) {
        _searchText.value = query
        if (query.length > 1 && _users.value !is ResourceState.Loading)
            getUsers(query)
    }

    private fun getUsers(query: String) = viewModelScope.launch(Dispatchers.IO) {
        delay(THROTTLE_DURATION)
        usersUseCase.getUsers(query).collectLatest { users ->
            _users.value = users.mapSuccess {
                it.items.map { entry -> entry.toUiModel() }
            }
        }
    }

    private fun UserEntry.toUiModel() =
        UserItemUiModel(login, type, id, avatarUrl)
}
