package com.casey.mindmoney.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.casey.mindmoney.components.MainScaffold
import com.casey.mindmoney.navigation.NavigationRoutes
import com.casey.mindmoney.ui.theme.AppTheme
import com.casey.mindmoney.ui.theme.errorLight
import com.casey.mindmoney.ui.theme.tertiaryLight

@Composable
fun RevenueExpensesScreen(navController: NavHostController) {
    val revenueTotal = 2072
    val expenseTotal = 440
    val goalSpending = 300
    val remaining = revenueTotal - expenseTotal - goalSpending

    MainScaffold(navController, NavigationRoutes.MANAGE) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Revenue section
            SectionBox(title = "Revenue", borderColor = tertiaryLight) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Revenue", style = MaterialTheme.typography.titleMedium)
                    Icon(Icons.Default.Add, contentDescription = "Add Revenue", tint = MaterialTheme.colorScheme.primary)
                }
                Text("• Wage: \$1970")
                Text("• Stocks: \$102")
            }

            // Expenses section
            SectionBox(title = "Expenses", borderColor = errorLight) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Expenses", style = MaterialTheme.typography.titleMedium)
                    Icon(Icons.Default.Add, contentDescription = "Add Expense", tint = MaterialTheme.colorScheme.primary)
                }
                Text("• Groceries: \$240")
                Text("• Utilities: \$120")
                Text("• Transport: \$80")
            }

            // Goals section (NEW)
            SectionBox(title = "Goals", borderColor = Color(0xFF8E24AA)) {
                Text("• Goal Spending: \$${goalSpending}")
            }

            // Remaining section (NEW)
            HorizontalDivider()
            Text(
                "Remaining Money: \$${remaining}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SectionBox(title: String, borderColor: Color, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, borderColor, shape = MaterialTheme.shapes.medium)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = content
    )
}


@Preview(name = "Preview Mode", showBackground = true, showSystemUi = true)
@Composable
fun RevenueExpensesScreen() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        val dummyNavController = rememberNavController()
        RevenueExpensesScreen(navController = dummyNavController)
    }
}
