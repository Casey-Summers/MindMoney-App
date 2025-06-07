package com.casey.mindmoney.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
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
import com.casey.mindmoney.ui.theme.primaryLight
import com.casey.mindmoney.ui.theme.tertiaryLight

@Composable
fun SuggestionsScreen(navController: NavHostController) {
    val revenue = 2072
    val expenses = 1440
    val rank = "Top 70%"
    val thresholds = mapOf(
        "Groceries" to 300,
        "Entertainment" to 150,
        "Bills" to 400
    )

    val suggestions = listOf(
        Suggestion(
            icon = Icons.Default.CheckCircle,
            title = "Groceries Spending",
            text = "You’ve spent \$240 on Groceries this week. You’re within your \$${thresholds["Groceries"]} limit. Good job!",
            color = tertiaryLight
        ),
        Suggestion(
            icon = Icons.Default.Warning,
            title = "Entertainment Over Budget",
            text = "Your Entertainment spending is \$180, which is above your \$${thresholds["Entertainment"]} threshold. Consider reducing outings.",
            color = errorLight
        ),
        Suggestion(
            icon = Icons.Default.CheckCircle,
            title = "Bill Tracking",
            text = "Bills total \$390, just under your \$${thresholds["Bills"]} allowance. Keep tracking recurring charges.",
            color = tertiaryLight
        ),
        Suggestion(
            icon = Icons.Default.BarChart,
            title = "Spending Summary",
            text = "Your total expenses this cycle are \$${expenses}, leaving \$${revenue - expenses} available. Consider allocating \$200 to goals.",
            color = primaryLight
        ),
        Suggestion(
            icon = Icons.Default.AccountCircle,
            title = "Spending Rank",
            text = "Based on your rank ($rank), your spending habits are better than average. Aim for Top 75% next cycle.",
            color = primaryLight
        )
    )

    MainScaffold(navController, NavigationRoutes.SUGGESTIONS) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Smart Suggestions", style = MaterialTheme.typography.titleLarge)

            suggestions.forEach { suggestion ->
                SuggestionCard(suggestion)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

data class Suggestion(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val title: String,
    val text: String,
    val color: Color
)

@Composable
fun SuggestionCard(suggestion: Suggestion) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(suggestion.color.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = suggestion.icon,
            contentDescription = null,
            tint = suggestion.color,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(28.dp)
        )
        Column {
            Text(suggestion.title, style = MaterialTheme.typography.titleMedium, color = suggestion.color)
            Spacer(modifier = Modifier.height(4.dp))
            Text(suggestion.text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SuggestionsScreenPreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        val dummyNavController = rememberNavController()
        SuggestionsScreen(navController = dummyNavController)
    }
}
