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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.calorify.app.ui.theme.CalorifyTheme

@Composable
fun MealTimeSelection(
    modifier: Modifier = Modifier,
    MealTimeSelected: String,
    onMealSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedMealTime by remember { mutableStateOf(MealTimeSelected) }
    val mealTime = listOf("Sarapan", "Makan Siang", "Makan Malam", "Lain-Lain")

    OutlinedTextField(
        value = selectedMealTime,
        onValueChange = { onMealSelected(selectedMealTime) },
        modifier = modifier.fillMaxWidth(),
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
                mealTime.forEach { mealTime ->
                    DropdownMenuItem(
                        onClick = {
                            selectedMealTime = mealTime
                            onMealSelected(mealTime)
                            expanded = false
                        }
                    ) {
                        Text(
                            text = mealTime,
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

@Preview(showBackground = true)
@Composable
fun MealTimeSelectionPreview() {
    CalorifyTheme {

    }
}