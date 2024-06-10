package com.example.opsc7311.ui.screens.stats

import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.opsc7311.util.Converters
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.DefaultColors
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.composed.plus
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.plus
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun StatsScreenContent(
    statsScreenUiState: StatsScreenUiState,
    data: Map<String, Double>,
    minGoal: Int,
    maxGoal: Int,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {





        val xValuesToDates = data.keys.associateBy { getEpochDay(Converters.dateToTextDisplay.parse
            (it))
            .toFloat() }


        val chartEntryModel1 = xValuesToDates.keys.zip(data.values) { x, y -> entryOf(x, y) }
            .let { entryModelOf(it) }


        val inGoals = chartEntryModel1.entries[0].filter { entry ->
            entry.y in (minGoal.toFloat()..maxGoal.toFloat())
        }

        val horizontalAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value,
                                                                                          _ ->
            ((SimpleDateFormat("dd/MM").format(getDateFromEpochDay
                (value.toLong()))).toString())
        }

        val thresholdLine1 = rememberThresholdLine(minGoal = minGoal.toFloat(), maxGoal = maxGoal
            .toFloat())

        val chart1 = columnChart(
            decorations = listOf(thresholdLine1)
        )

        Log.d("aaaa-chart-1", chartEntryModel1.toString())

        Chart(
            chart = chart1,
            model = chartEntryModel1,
            startAxis = startAxis(),
            bottomAxis = bottomAxis(valueFormatter = horizontalAxisValueFormatter),
            chartScrollState = rememberChartScrollState()
        )

        Text(
            modifier = Modifier.padding(16.dp),
            text = "This graph shows the total duration worked during a period. The yellow bar is" +
                    " " +
                    "your " +
                    "current minimum and maximum daily goals.",
            fontSize = 16.sp
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
            text = "You were on track ${inGoals.count()} times during this period.",
            fontSize = 18.sp
        )

    }

}
private fun getEpochDay(date: Date?): Long {
    if(date == null) return 0
    val calendar = Calendar.getInstance()
    calendar.time = date
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val epochStart = Date(70, 0, 1) // January 1, 1970
    val epochCalendar = Calendar.getInstance()
    epochCalendar.time = epochStart
    epochCalendar.set(Calendar.HOUR_OF_DAY, 0)
    epochCalendar.set(Calendar.MINUTE, 0)
    epochCalendar.set(Calendar.SECOND, 0)
    epochCalendar.set(Calendar.MILLISECOND, 0)

    val epochDayInMillis = 24 * 60 * 60 * 1000 // Number of milliseconds in a day
    val diffInMillis = calendar.timeInMillis - epochCalendar.timeInMillis

    return diffInMillis / epochDayInMillis
}

fun getDateFromEpochDay(epochDay: Long): Date {
    val epochStart = Date(70, 0, 1) // January 1, 1970
    val epochCalendar = Calendar.getInstance()
    epochCalendar.time = epochStart
    epochCalendar.set(Calendar.HOUR_OF_DAY, 0)
    epochCalendar.set(Calendar.MINUTE, 0)
    epochCalendar.set(Calendar.SECOND, 0)
    epochCalendar.set(Calendar.MILLISECOND, 0)

    val epochDayInMillis = 24 * 60 * 60 * 1000 // Number of milliseconds in a day
    val epochDayInMillisLong = epochDay * epochDayInMillis
    val dateInMillis = epochCalendar.timeInMillis + epochDayInMillisLong

    return Date(dateInMillis)
}

@Composable
private fun rememberThresholdLine(minGoal: Float, maxGoal: Float): ThresholdLine {
    val label = textComponent(
        color = Color.Black,
        background = shapeComponent(Shapes.rectShape, Color(0xffe9e5af)),
        padding = dimensionsOf(horizontal = 8.dp, vertical = 2.dp),
        margins = dimensionsOf(all = 4.dp),
        typeface = Typeface.MONOSPACE,
    )
    val line = shapeComponent(color = Color(0xffe9e5af).copy(alpha = .36F))
    return remember(label, line) {
        ThresholdLine(thresholdRange = minGoal..maxGoal, labelComponent = label, lineComponent = line)
    }
}

@Composable
@Preview
fun StatsScreenContentPreview()
{

    val dataMap = mutableMapOf<String, Double>()

    StatsScreenContent(
        statsScreenUiState = StatsScreenUiState(),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        data = dataMap,
        minGoal = 1,
        maxGoal = 10
    )
}

