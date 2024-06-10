package com.example.opsc7311.ui.screens.goals

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import kotlin.math.ceil
import kotlin.math.floor


@Composable
fun GoalScreenContent(
    goalScreenUiState: GoalScreenUiState,
    onMinDailyGoalSliderChanged: (Float) -> Unit,
    onMinDailyGoalSliderFinishChange: () -> Unit,
    onMaxDailyGoalSliderChanged: (Float) -> Unit,
    onMaxDailyGoalSliderFinishChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card {
            Text(
                modifier = Modifier.padding(4.dp),
                text = goalScreenUiState.prompt,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        ProgressCircle(
            percentFilled = (goalScreenUiState.percentHoursWorkedToGoal),
            hoursSpent = goalScreenUiState.hoursWorkedToday,
            modifier = Modifier.padding(top = 32.dp)
        )

        Spacer(modifier = Modifier.padding(top = 32.dp))

        StepsSlider(
            text = "Minimum Daily Goal",
            sliderPosition = goalScreenUiState.minDailyGoalSliderValue,
            sliderHours = goalScreenUiState.minDailyGoal,
            onValueChange = onMinDailyGoalSliderChanged,
            sliderRange = goalScreenUiState.minSliderRange,
            onValueChangeFinished = onMinDailyGoalSliderFinishChange
        )

        StepsSlider(
            text = "Maximum Daily Goal",
            sliderPosition = goalScreenUiState.maxDailyGoalSliderValue,
            sliderHours = goalScreenUiState.maxDailyGoal,
            onValueChange = onMaxDailyGoalSliderChanged,
            sliderRange = 1F..24F,
            onValueChangeFinished = onMaxDailyGoalSliderFinishChange
        )


    }
}

@Composable
fun StepsSlider(
    text: String,
    sliderPosition: Float,
    sliderHours: Int,
    sliderRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit
) {

    val startColor = Color(0xFF90EE90)
    val endColor = Color.Red

    // Interpolate between the start and end colors based on the slider value
    val blend = ColorUtils.blendARGB(
        startColor.toArgb(), endColor.toArgb(), skewValue
            (sliderPosition / 24)
    )

    // Customize the color of the Slider
    val sliderColors = SliderDefaults.colors(
        thumbColor = MaterialTheme.colorScheme.primary,
        activeTrackColor = Color(blend),
        inactiveTrackColor = Color.LightGray
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "$text: $sliderHours")
        Slider(
            value = sliderPosition,
            onValueChange = onValueChange,
            valueRange = sliderRange,
            onValueChangeFinished = onValueChangeFinished,
            steps = ceil(sliderRange.endInclusive).toInt() - floor(sliderRange.start).toInt() - 1,
            colors = sliderColors
        )
    }
}


@OptIn(ExperimentalTextApi::class)
@Composable
fun ProgressCircle(percentFilled: Float, hoursSpent: Int, modifier: Modifier = Modifier) {

    val textMeasurer1 = rememberTextMeasurer()
    val textMeasurer2 = rememberTextMeasurer()

    val hoursString = buildAnnotatedString {


        withStyle(
            style = MaterialTheme.typography.displaySmall.copy(
                color = MaterialTheme
                    .colorScheme.onSurface
            ).toSpanStyle()
        ) {
            append("$hoursSpent")
        }

    }

    val companionString = buildAnnotatedString {


        withStyle(
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme
                    .colorScheme.onSurface
            ).toSpanStyle()
        ) {
            append("Hours Today")
        }

    }

    val hoursDimensions = textMeasurer1.measure(hoursString)
    val companionDimensions = textMeasurer2.measure(companionString)
    val totalHeight = hoursDimensions.size.height + companionDimensions.size.height

    Canvas(
        modifier = modifier
            .requiredSizeIn(200.dp, 200.dp, 200.dp, 200.dp)
            .aspectRatio(1.0f)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val centerX = canvasWidth / 2
        val centerY = canvasHeight / 2
        val radius = canvasWidth / 2 * 0.8f
        val startAngle = -90f
        val sweepAngle = percentFilled / 100f * 360f

        // Draw the circle
        drawCircle(
            color = Color.LightGray,
            radius = radius,
            center = Offset(centerX, centerY),
            style = Stroke(width = 50f)
        )

        // Define the start and end colors for the gradient
        val startColor = Color(0xFFADD8E6)
        val endColor = Color(0xFF90EE90)


        // Interpolate between the start and end colors based on the percentFilled value
        val blend = if (percentFilled > 100F) {
            ColorUtils.blendARGB(startColor.toArgb(), endColor.toArgb(), 1F)
        } else {
            ColorUtils.blendARGB(startColor.toArgb(), endColor.toArgb(), percentFilled / 100)
        }

        drawArc(
            color = Color(color = blend),
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(centerX - radius, centerY - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = 50f, cap = StrokeCap.Round)
        )

        drawText(
            textMeasurer = textMeasurer1,
            text = hoursString,
            topLeft = Offset(
                (canvasWidth - hoursDimensions.size.width) / 2,
                center.y - totalHeight / 2
            )
        )

        drawText(
            textMeasurer = textMeasurer2,
            text = companionString,
            topLeft = Offset(
                (canvasWidth - companionDimensions.size.width) / 2,
                center.y + totalHeight / 2 - companionDimensions.size.height
            )
        )


    }
}

private fun skewValue(x: Float): Float {
    val a = 14f // Adjust this parameter to control the shape of the curve
    val b = 0.7f // Adjust this parameter to control the midpoint of the curve
    return (1 / (1 + kotlin.math.exp(-a * (x - b))))
}

@Composable
@Preview
fun GoalScreenContentPreview() {
    GoalScreenContent(
        goalScreenUiState = GoalScreenUiState(),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        onMinDailyGoalSliderChanged = {},
        onMinDailyGoalSliderFinishChange = {},
        onMaxDailyGoalSliderChanged = {},
        onMaxDailyGoalSliderFinishChange = {},
    )
}