package com.calorify.app.ui.component

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
fun CustomButton(
    icon: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .height(48.dp)) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Blue500,
            contentColor = White
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon), // Replace with your icon resource
                contentDescription = "Left Icon"
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.button.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_semibold),
                ),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    CalorifyTheme {
        CustomButton(R.drawable.ic_add_circle_outline_24, "Tes", onClick = {})
    }
}