package com.calorify.app.ui.component.graph

import android.graphics.Typeface
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.calorify.app.R
import com.calorify.app.data.local.LineChartData
import com.calorify.app.data.local.getLineChartData
import com.calorify.app.ui.theme.Blue200
import com.calorify.app.ui.theme.Blue700
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Neutral500
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlin.math.log


@Composable
fun LineGraph(
    monthlyCalorieFulFilled: Map<String, Int>,
) {
    Column() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Asupan Kalori (kal)",
                fontFamily = FontFamily(Font(R.font.inter_medium)),
                fontSize = 14.sp,
                color = Neutral500,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Start)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(200.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Crossfade(targetState = monthlyCalorieFulFilled) { lineChartData ->
                    AndroidView(factory = { context ->
                        LineChart(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            this.description.isEnabled = false
                        }
                    },
                        modifier = Modifier
                            .wrapContentSize(), update = {
                            Log.d("LINECHART", "LineGraph: $lineChartData")
                            updateLineChartWithData(it, lineChartData)
                        })
                }
            }
            Text(
                text = "Tanggal",
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_medium),
                ),
                fontSize = 12.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

fun updateLineChartWithData(
    chart: LineChart,
    data: Map<String, Int>,
) {

    val entries = ArrayList<Entry>()

    for (i in data.keys) {
        val item = data[i]
        entries.add(Entry(i.toFloat(), item?.toFloat() ?: 0.toFloat()))
    }

    val ds = LineDataSet(entries, "")
    ds.setDrawValues(false)


    ds.colors = arrayListOf(
        Blue200.toArgb(),
    )

    ds.valueTextSize = 12F

    ds.valueTypeface = Typeface.DEFAULT_BOLD

    val colors = listOf(
        Blue700.toArgb(),
    )

    ds.setDrawFilled(true)
    ds.fillColor = Blue200.toArgb()

    ds.setValueTextColors(colors)

    val yAxisRight = chart.axisRight
    yAxisRight.isEnabled = false

    val yAxisLeft = chart.axisLeft
    yAxisLeft.textSize = 12f

    val xAxis = chart.xAxis

    xAxis.position = XAxis.XAxisPosition.BOTTOM
    xAxis.mAxisRange = data.size.toFloat()
    xAxis.granularity = 1f
    xAxis.textSize = 12f

    chart.isDragEnabled = true
    chart.setScaleEnabled(true)
    chart.setPinchZoom(true)

    val viewportOffset = 0.5f
    val viewportWidth = 5f
    val start = 0f - viewportOffset


    chart.setVisibleXRangeMaximum(viewportWidth)
    chart.moveViewToX(start)


    chart.xAxis.setDrawGridLines(false)

    val d = LineData(ds)
    chart.data = d
    chart.legend.isEnabled = false

    chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
        override fun onValueSelected(e: Entry?, h: Highlight?) {
            ds.setDrawValues(true)
            chart.invalidate()
        }

        override fun onNothingSelected() {
            ds.setDrawValues(false)
            chart.invalidate()
        }
    })

    chart.invalidate()
}