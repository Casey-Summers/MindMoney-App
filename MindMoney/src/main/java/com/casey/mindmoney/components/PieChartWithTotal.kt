package com.casey.mindmoney.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PieChartWithTotal(
    label: String,
    total: Float,
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.inversePrimary
    )
) {
    val dummyData = listOf(40f, 30f, 20f, 10f)
    val totalSum = dummyData.sum()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
        Box(modifier = Modifier.size(80.dp)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                var startAngle = 0f
                val canvasSize = size
                dummyData.forEachIndexed { index, value ->
                    val sweepAngle = (value / totalSum) * 360f
                    drawArc(
                        color = colors.getOrElse(index) { Color.Gray },
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset.Zero,
                        size = Size(canvasSize.width, canvasSize.height)
                    )
                    startAngle += sweepAngle
                }
            }
        }
        Text("$${total.toInt()}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
    }
}
