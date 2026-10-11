package com.khan.journalapplication.presentation.BreatheScreen


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khan.journalapplication.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin

private const val PHASE_SECONDS = 4
private const val PHASE_MILLIS = PHASE_SECONDS * 1_000
private const val TOTAL_CYCLES = 4

private enum class BreathPhase(val instruction: String) {
    Inhale("INHALE"),
    HoldIn("HOLD"),
    Exhale("EXHALE"),
    HoldOut("HOLD")
}

@Composable
fun BreatheScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = colorResource(R.color.app_background)
    val surfaceColor = colorResource(R.color.app_surface)
    val textColor = colorResource(R.color.app_text)
    val accentColor = colorResource(R.color.app_accent)

    var currentPhase by remember { mutableStateOf(BreathPhase.Inhale) }
    var currentCycle by remember { mutableIntStateOf(1) }
    var isComplete by remember { mutableStateOf(false) }
    var sessionKey by remember { mutableIntStateOf(0) }

    val phaseProgress = remember { Animatable(0f) }
    val circleScale = remember { Animatable(0.68f) }
    var currentPhaseIndex by remember { mutableIntStateOf(0) }

    var hasStarted by remember { mutableStateOf(false) }
    var isCountingDown by remember { mutableStateOf(false) }
    var countdown by remember { mutableIntStateOf(5) }

    LaunchedEffect(sessionKey) {
        if (!hasStarted) return@LaunchedEffect

        isComplete = false
        isCountingDown = true
        countdown = 5

        repeat(5) {
            delay(1_000)
            countdown--
        }

        isCountingDown = false

        circleScale.snapTo(0.68f)
        phaseProgress.snapTo(0f)
        currentPhaseIndex = 0
        currentPhase = BreathPhase.Inhale
        currentCycle = 1

        repeat(TOTAL_CYCLES) { cycleIndex ->
            currentCycle = cycleIndex + 1

            BreathPhase.entries.forEachIndexed { phaseIndex, nextPhase ->
                currentPhaseIndex = phaseIndex
                currentPhase = nextPhase
                phaseProgress.snapTo(0f)

                coroutineScope {
                    launch {
                        phaseProgress.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(
                                durationMillis = PHASE_MILLIS,
                                easing = LinearEasing
                            )
                        )
                    }

                    launch {
                        when (nextPhase) {
                            BreathPhase.Inhale -> {
                                circleScale.animateTo(
                                    targetValue = 1f,
                                    animationSpec = tween(
                                        durationMillis = PHASE_MILLIS,
                                        easing = LinearEasing
                                    )
                                )
                            }

                            BreathPhase.Exhale -> {
                                circleScale.animateTo(
                                    targetValue = 0.68f,
                                    animationSpec = tween(
                                        durationMillis = PHASE_MILLIS,
                                        easing = LinearEasing
                                    )
                                )
                            }

                            BreathPhase.HoldIn,
                            BreathPhase.HoldOut -> {
                                delay(PHASE_MILLIS.toLong())
                            }
                        }
                    }
                }
            }
        }

        isComplete = true
    }

    val secondsRemaining = ceil(
        PHASE_SECONDS * (1f - phaseProgress.value).toDouble()
    ).toInt().coerceIn(1, PHASE_SECONDS)

    val ringProgress = if (isComplete) {
        1f
    } else {
        (currentPhaseIndex + phaseProgress.value) / BreathPhase.entries.size.toFloat()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Return home",
                tint = textColor
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when {
                !hasStarted -> {
                    Text(
                        text = "BREATHE",
                        color = textColor,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(48.dp))

                    Surface(
                        modifier = Modifier.size(230.dp),
                        shape = CircleShape,
                        color = surfaceColor
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Air,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = accentColor
                            )

                            Spacer(Modifier.height(20.dp))

                            Text(
                                text = "Are you ready?",
                                color = textColor,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(Modifier.height(40.dp))

                    Button(
                        onClick = {
                            isCountingDown = true
                            hasStarted = true
                            sessionKey++
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = textColor,
                            contentColor = surfaceColor
                        )
                    ) {
                        Text(
                            text = "I'm ready",
                            fontSize = 18.sp
                        )
                    }

                    TextButton(onClick = onBackClick) {
                        Text(
                            text = "Not right now",
                            color = textColor
                        )
                    }
                }

                isCountingDown -> {
                    Text(
                        text = "GET READY",
                        color = textColor,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(48.dp))

                    Surface(
                        modifier = Modifier.size(230.dp),
                        shape = CircleShape,
                        color = surfaceColor
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = countdown.toString(),
                                color = textColor,
                                fontSize = 80.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(Modifier.height(32.dp))

                    Text(
                        text = "Breathing begins in...",
                        color = textColor,
                        fontSize = 22.sp
                    )
                }

                else -> {
                    Text(
                        text = "BREATHE",
                        color = textColor,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(48.dp))

                    Box(
                        modifier = Modifier.size(280.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        BreathingTimerRing(
                            progress = ringProgress,
                            ringColor = textColor,
                            modifier = Modifier.fillMaxSize()
                        )

                        Surface(
                            modifier = Modifier
                                .size(190.dp)
                                .graphicsLayer {
                                    scaleX = circleScale.value
                                    scaleY = circleScale.value
                                },
                            shape = CircleShape,
                            color = surfaceColor
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Air,
                                    contentDescription = null,
                                    modifier = Modifier.size(72.dp),
                                    tint = accentColor
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(42.dp))

                    Text(
                        text = if (isComplete) {
                            "COMPLETE"
                        } else {
                            currentPhase.instruction
                        },
                        color = textColor,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(Modifier.height(8.dp))

                    if (!isComplete) {
                        Text(
                            text = "$secondsRemaining",
                            color = textColor,
                            fontSize = 24.sp
                        )

                        Text(
                            text = "Cycle $currentCycle of $TOTAL_CYCLES",
                            color = textColor.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )
                    } else {
                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = {
                                hasStarted = false
                                isComplete = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = textColor,
                                contentColor = surfaceColor
                            )
                        ) {
                            Text("Breathe again")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BreathingTimerRing(
    progress: Float,
    ringColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 3.dp.toPx()
        val radius = (size.minDimension - strokeWidth) / 2f
        val centerPoint = Offset(size.width / 2f, size.height / 2f)

        drawCircle(
            color = ringColor.copy(alpha = 0.3f),
            radius = radius,
            center = centerPoint,
            style = Stroke(width = strokeWidth)
        )

        drawArc(
            color = ringColor,
            startAngle = -90f,
            sweepAngle = 360f * progress,
            useCenter = false,
            topLeft = Offset(
                x = centerPoint.x - radius,
                y = centerPoint.y - radius
            ),
            size = Size(radius * 2f, radius * 2f),
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )

        val markerAngle = Math.toRadians(
            (-90f + 360f * progress).toDouble()
        )

        val markerPosition = Offset(
            x = centerPoint.x + radius * cos(markerAngle).toFloat(),
            y = centerPoint.y + radius * sin(markerAngle).toFloat()
        )

        drawCircle(
            color = ringColor,
            radius = 8.dp.toPx(),
            center = markerPosition
        )
    }
}