package com.calorify.app.ui.component.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calorify.app.R
import com.calorify.app.data.local.MonthDict
import com.calorify.app.ui.theme.Neutral500

@Composable
fun MonthSelection(
    monthSelected: String,
    onMonthSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Pilih Bulan",
            fontFamily = FontFamily(Font(R.font.inter_medium)),
            fontSize = 14.sp,
            color = Neutral500,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        var expanded by remember { mutableStateOf(false) }
        var selectedMonth by remember { mutableStateOf(MonthDict.numMapToMonth[monthSelected]!!) }
        val months = listOf(
            "Januari",
            "Februari",
            "Maret",
            "April",
            "Mei",
            "Juni",
            "Juli",
            "Agustus",
            "September",
            "Oktober",
            "November",
            "Desember"
        ) // Add other months

        OutlinedTextField(
            value = selectedMonth,
            onValueChange = { onMonthSelected(selectedMonth) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
            trailingIcon = {
                Icon(
                    if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            readOnly = true
        )

        if (expanded) {
            Surface(
                shape = RoundedCornerShape(4.dp),
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    months.forEach { month ->
                        DropdownMenuItem(
                            onClick = {
                                selectedMonth = month
                                onMonthSelected(month)
                                expanded = false
                            }
                        ) {
                            Text(
                                text = month,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}