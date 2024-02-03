package com.sa.githubers.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sa.githubers.R
import com.sa.githubers.ui.UserItemUiModel
import com.sa.githubers.ui.theme.dimens

@Composable
fun SearchFieldComponent(
    searchText: String,
    onValueChange: (String) -> Unit
) {
    Card(Modifier.padding(MaterialTheme.dimens.medium)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                maxLines = 1,
                value = searchText,
                onValueChange = onValueChange,
                label = { Text(text = stringResource(R.string.start_typing_to_search)) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.TwoTone.Search,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
    }
}

@Composable
fun UsersList(userList: List<UserItemUiModel>, onUserClicked: (login: String) -> Unit) {
    LazyColumn {
        items(
            count = userList.size,
            key = { userList[it].id },
            itemContent = { UserItem(userList[it], onUserClicked) }
        )
    }
}

@Composable
fun UserItem(user: UserItemUiModel, onUserClicked: (login: String) -> Unit) {
    Card(
        Modifier
            .padding(
                horizontal = MaterialTheme.dimens.medium,
                vertical = MaterialTheme.dimens.small
            )
            .clickable { onUserClicked(user.login) }) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                placeholder = rememberVectorPainter(image = Icons.Outlined.Warning),
                error = rememberVectorPainter(image = Icons.Outlined.Warning),
                model = user.avatarUrl,
                contentDescription = stringResource(R.string.user_avatar_content_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(MaterialTheme.dimens.profileThumbnailSize)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Column(
                Modifier.padding(start = MaterialTheme.dimens.medium),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = user.login,
                    Modifier.wrapContentWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = user.type,
                    Modifier.wrapContentWidth(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
