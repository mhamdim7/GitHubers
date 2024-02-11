package com.sa.githubers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sa.githubers.AppConstants.THROTTLE_DURATION
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.domain.usecases.UserListUseCase
import com.sa.githubers.ui.mapper.UserListDomainToUiMapper
import com.sa.githubers.ui.model.UserItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase,
    private val mapper: UserListDomainToUiMapper
) : ViewModel() {

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
        userListUseCase.getUsers(query).collectLatest { users ->
            _users.value = mapper.mapFrom(users)
        }
    }
}
