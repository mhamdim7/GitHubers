package com.sa.githubers.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sa.githubers.R
import com.sa.githubers.ui.theme.dimens


@Composable
fun ProgressLoader() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.inversePrimary)
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.medium))
        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun EmptyStateComponent() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.no_users_found),
            style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ErrorComponent(message: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.error_text, message),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}


@Composable
private fun AnimateShapeInfinitely(
    animateShape: Animatable<Float, AnimationVector1D>,
    targetValue: Float = 1f,
    durationMillis: Int = 1000
) {
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }
}

/**
 * Animation looks like a search icon infinitely framing itself.
 *
 * animateCircle property -> animates circle in search.
 * animateLine property   -> animates floating/growing line and joins circle to form search
 * looking icon at the end.
 */
@Composable
fun AnimatedSearchIcon() {

    // Simple progressive circle looking animation
    val animateCircle = remember { Animatable(0f) }.apply {
        AnimateShapeInfinitely(this)
    }

    // 0.6f for initial value to reduce floating time of line to reach it's final state.
    // Settings it to 0f -> final animation output looks kind of aggressive movements.
    val animateLine = remember { Animatable(0.6f) }.apply {
        AnimateShapeInfinitely(this)
    }

    // Appears different for dark/light theme colors.
    val surfaceColor = MaterialTheme.colorScheme.onSurface

    // Arcs & Line drawn in canvas at animation final state looks like search icon.
    Canvas(
        modifier = Modifier
    ) {
        drawArc(
            color = surfaceColor,
            startAngle = 45f,
            sweepAngle = 360f * animateCircle.value,
            useCenter = false,
            size = Size(80f, 80f),
            style = Stroke(16f, cap = StrokeCap.Round)
        )

        drawLine(
            color = surfaceColor,
            strokeWidth = 16f,
            cap = StrokeCap.Round,
            start = Offset(
                animateLine.value * 80f,
                animateLine.value * 80f
            ),
            end = Offset(
                animateLine.value * 110f,
                animateLine.value * 110f
            )
        )
    }
}