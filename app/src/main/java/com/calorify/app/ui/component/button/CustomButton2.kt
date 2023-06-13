package com.calorify.app.ui.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calorify.app.R
import com.calorify.app.ui.theme.Blue500
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.White

@Composable
fun CustomButton2(
    text: String,
    color: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
) {
    Box(
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.medium)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 20.dp
            ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = color,
                contentColor = contentColor
            )

        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.button.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(
                        Font(resId = R.font.inter_semibold)
                    ),
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButton2Preview() {
    CalorifyTheme {
        CustomButton2("Tes Button", color = Blue500 ,contentColor = White, onClick = {})
    }
}