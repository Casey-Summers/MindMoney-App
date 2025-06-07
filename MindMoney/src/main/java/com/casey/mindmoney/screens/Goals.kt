package com.casey.mindmoney.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.casey.mindmoney.components.MainScaffold
import com.casey.mindmoney.navigation.NavigationRoutes
import com.casey.mindmoney.ui.theme.AppTheme
import com.casey.mindmoney.ui.theme.errorLight
import com.casey.mindmoney.ui.theme.tertiaryLight

data class Goal(
    val title: String,
    val current: Int,
    val target: Int
)

@Composable
fun GoalsScreen(navController: NavHostController) {
    val mainGoal = Goal("Buy a House", 50000, 60000)
    val otherGoals = listOf(
        Goal("Holiday Trip", 1200, 2000),
        Goal("Emergency Fund", 300, 1000),
        Goal("Laptop Upgrade", 800, 1500)
    )

    MainScaffold(navController, NavigationRoutes.GOALS) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Main Goal section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(tertiaryLight.copy(alpha = 0.1f))
                    .padding(16.dp)
            ) {
                Text("Main Goal: ${mainGoal.title}", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                GoalProgressBar(goal = mainGoal)
            }

            // Other Goals
            otherGoals.forEach { goal ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(goal.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    GoalProgressBar(goal = goal)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun GoalProgressBar(goal: Goal) {
    val progress = goal.current.toFloat() / goal.target
    val progressColor = if (progress >= 0.8f) {
        tertiaryLight
    } else {
        errorLight
    }

    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp),
        color = progressColor
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        "${goal.current} / ${goal.target} (${(progress * 100).toInt()}%)",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Preview(name = "Goals Preview", showBackground = true, showSystemUi = true)
@Composable
fun GoalsScreenPreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        val dummyNavController = rememberNavController()
        GoalsScreen(navController = dummyNavController)
    }
}
