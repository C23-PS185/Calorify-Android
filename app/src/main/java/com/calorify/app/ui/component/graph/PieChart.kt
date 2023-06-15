package com.calorify.app.ui.component.graph

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.calorify.app.R
import com.calorify.app.data.local.PieChartData
import com.calorify.app.ui.theme.Blue200
import com.calorify.app.ui.theme.Blue500
import com.calorify.app.ui.theme.Blue700
import com.calorify.app.ui.theme.Green200
import com.calorify.app.ui.theme.Green700
import com.calorify.app.ui.theme.Orange200
import com.calorify.app.ui.theme.Orange700
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.abs

// Reference: https://www.geeksforgeeks.org/pie-chart-in-android-using-jetpack-compose/
@Composable
fun PieChart(calorieNeeded: Int, calorieFulfilled: Int) {
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
                text = "Kebutuhan kalori total: $calorieNeeded kal",
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
                val otherCalorie = calorieNeeded - calorieFulfilled
                var otherStatus = ""
                var newCalorieFulfilled = calorieFulfilled

                if (otherCalorie >= 0) {
                    otherStatus = "Kalori Dibutuhkan"
                } else {
                    otherStatus = "Kalori Berlebih"
                    newCalorieFulfilled = calorieFulfilled + 2 * otherCalorie
                }
                val otherData = PieChartData(otherStatus, abs(otherCalorie))
                val dataFulfilled = PieChartData("Kalori Terpenuhi", newCalorieFulfilled)
                Crossfade(
                    targetState =
                    if (calorieFulfilled == 0) {
                        listOf(otherData)
                    } else {
                        listOf(dataFulfilled, otherData)
                    }
                ) { pieChartData ->
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


fun updatePieChartWithData(

    chart: PieChart,
    data: List<PieChartData>
) {

    val entries = ArrayList<PieEntry>()


    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value!!.toFloat() ?: 0.toFloat(), item.status ?: ""))
    }


    val ds = PieDataSet(entries, "")


    if (data.size == 1) {
        ds.colors = arrayListOf(
            Blue200.toArgb(),
        )
        ds.setValueTextColors(
            listOf(
                Blue700.toArgb(),
            )
        )
    } else if (data[0].status == "Kalori Berlebih" || data[1].status == "Kalori Berlebih") {
        ds.colors = arrayListOf(
            Blue200.toArgb(),
            Orange200.toArgb(),
        )
        ds.setValueTextColors(
            listOf(
                Blue700.toArgb(),
                Orange700.toArgb(),
            )
        )
    } else {
        ds.colors = arrayListOf(
            Green200.toArgb(),
            Blue200.toArgb(),
            Orange200.toArgb(),
        )

        ds.setValueTextColors(
            listOf(
                Green700.toArgb(),
                Blue700.toArgb(),
            )
        )
    }

    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.sliceSpace = 2f

    ds.valueTextSize = 20f

    ds.valueTypeface = Typeface.DEFAULT_BOLD


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