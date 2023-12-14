package com.sa.githubers.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.sa.githubers.ui.RepoItemUiModel
import com.sa.githubers.ui.UserDetailsUiModel
import com.sa.githubers.ui.theme.dimens


@Composable
fun DetailsHeader(details: UserDetailsUiModel) {
    val hirable =
        if (details.hireable) stringResource(R.string.hirable) else stringResource(R.string.not_hirable)
    Card(Modifier.padding(MaterialTheme.dimens.large)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.large)
                .wrapContentHeight()
        ) {
            AsyncImage(
                placeholder = rememberVectorPainter(image = Icons.Outlined.Warning),
                error = rememberVectorPainter(image = Icons.Outlined.Warning),
                model = details.avatarUrl,
                contentDescription = stringResource(R.string.user_avatar_content_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(MaterialTheme.dimens.profileImageSize)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
            Text(
                details.name ?: "",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))
            Text(
                hirable,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
            Text(
                details.location ?: "",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
            Text(
                details.bio ?: "",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ReposList(items: List<RepoItemUiModel>) {
    LazyColumn {
        items(
            count = items.size,
            key = { items[it].id },
            itemContent = {
                RepoItem(items, it)
            }
        )
    }
}

@Composable
fun RepoItem(
    items: List<RepoItemUiModel>,
    it: Int
) {
    Card(Modifier.padding(MaterialTheme.dimens.small)) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.medium, vertical = MaterialTheme.dimens.small)
                .wrapContentHeight()
        ) {
            Text(
                items[it].name,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
            Text(
                items[it].description ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ReposTitle() {
    Card(
        Modifier
            .padding(MaterialTheme.dimens.small),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(0.75f),
        )
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.medium, vertical = MaterialTheme.dimens.small)
                .wrapContentHeight(),
        ) {
            Text(
                text = stringResource(R.string.repositories),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}