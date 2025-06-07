package com.casey.mindmoney.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.casey.mindmoney.components.AllowanceField
import com.casey.mindmoney.components.MainScaffold
import com.casey.mindmoney.components.PieChartWithTotal
import com.casey.mindmoney.navigation.NavigationRoutes
import com.casey.mindmoney.ui.theme.AppTheme

@Composable
fun HomePageScreen(navController: NavHostController) {
    MainScaffold(navController, NavigationRoutes.HOME) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Pie Charts
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PieChartWithTotal("Revenue", 1000f)
                PieChartWithTotal(
                    label = "Expenses",
                    total = 500f,
                    colors = listOf(
                        Color(0xFFFF8A65), Color(0xFFFF7043),
                        Color(0xFFF4511E), Color(0xFFE64A19)
                    )
                )
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Rank", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(Icons.Default.ArrowUpward, contentDescription = "Rank", tint = MaterialTheme.colorScheme.primary)
                    Text("Top 70%", color = MaterialTheme.colorScheme.onBackground)
                }
            }

            // Goal progress bar
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text("Goal Progress", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
                Text("Main Goal: Buy a house", color = MaterialTheme.colorScheme.tertiary, fontSize = 13.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LinearProgressIndicator(
                        progress = { 0.6f },
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        onClick = { navController.navigate(NavigationRoutes.SUGGESTIONS) }
                    ) {
                        Text("Suggestions")
                    }
                }
            }

            // Thresholds for weekly expenses
            Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text("Allowance Thresholds", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
                Text(
                    "Use these to set your rough spending budget for each category.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AllowanceField("Groceries")
                AllowanceField("Bills")
                AllowanceField("Entertainment")
                AllowanceField("Left Over")
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Preview(name = "Revenue & Expenses Preview", showBackground = true, showSystemUi = true)
@Composable
fun RevenueExpensesPreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        val dummyNavController = rememberNavController()
        RevenueExpensesScreen(navController = dummyNavController)
    }
}

