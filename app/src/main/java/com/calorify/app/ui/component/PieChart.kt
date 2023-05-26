package com.calorify.app.ui.component

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.calorify.app.data.local.PieChartData
import com.calorify.app.data.local.getPieChartData
import com.calorify.app.ui.theme.Black
import com.calorify.app.ui.theme.Blue200
import com.calorify.app.ui.theme.Blue700
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Orange200
import com.calorify.app.ui.theme.Orange700
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

@Composable
fun PieChart() {
    Column() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Asupan Kalori Hari Ini",
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_semibold),
                ),
                fontSize = 16.sp
            )
            Text(
                text = "Kebutuhan kalori total: 1288 kal",
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_regular),
                ),
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(160.dp)
                    .width(280.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Crossfade(targetState = getPieChartData) { pieChartData ->
                    AndroidView(factory = { context ->
                        PieChart(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            this.setDrawEntryLabels(false)
                            this.description.isEnabled = false
                            this.isDrawHoleEnabled = false
                            this.legend.isEnabled = true
                            this.legend.textSize = 14F
                            this.legend.horizontalAlignment =
                                Legend.LegendHorizontalAlignment.CENTER
                        }
                    },
                        modifier = Modifier
                            .wrapContentSize(), update = {
                            updatePieChartWithData(it, pieChartData)
                        })
                }
            }
        }
    }
}

// on below line we are creating a update pie
// chart function to update data in pie chart.
fun updatePieChartWithData(
    // on below line we are creating a variable
    // for pie chart and data for our list of data.
    chart: PieChart,
    data: List<PieChartData>
) {
    // on below line we are creating
    // array list for the entries.
    val entries = ArrayList<PieEntry>()

    // on below line we are running for loop for
    // passing data from list into entries list.
    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value ?: 0.toFloat(), item.status ?: ""))
    }

    // on below line we are creating
    // a variable for pie data set.
    val ds = PieDataSet(entries, "")

    // on below line we are specifying color
    // int the array list from colors.
    ds.colors = arrayListOf(
        Orange200.toArgb(),
        Blue200.toArgb(),
    )
    // on below line we are specifying position for value
    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    // on below line we are specifying position for value inside the slice.
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    ds.sliceSpace = 2f

    ds.valueTextSize = 20f

    ds.valueTypeface = Typeface.DEFAULT_BOLD

    val colors = listOf(
        Orange700.toArgb(),
        Blue700.toArgb(),
    )
    
    ds.setValueTextColors(colors)
    ds.valueFormatter = object : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return "${value.toInt()} kal"
        }
    }

    val d = PieData(ds)

    chart.data = d

    val legend = chart.legend
    legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
    legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
    legend.orientation = Legend.LegendOrientation.VERTICAL
    legend.setDrawInside(false)


    chart.invalidate()
}

@Preview(showBackground = true)
@Composable
fun PieChatPreview() {
    CalorifyTheme {
        PieChart()
    }
}