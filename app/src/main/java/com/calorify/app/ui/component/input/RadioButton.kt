package com.calorify.app.ui.component.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.calorify.app.ui.theme.CalorifyTheme

@Composable
fun RadioButtonType(modifier: Modifier = Modifier, onRadioButtonSelected: (String) -> Unit) {
    val radioOptions = listOf("Makanan", "Minuman")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Row(modifier = modifier.fillMaxWidth()) {
        radioOptions.forEach { text ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onRadioButtonSelected(text)
                        }
                    )
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                        onRadioButtonSelected(text)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Blue,
                        unselectedColor = Color.Black
                    )

                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RadioButtonPreview() {
    CalorifyTheme {

    }
}