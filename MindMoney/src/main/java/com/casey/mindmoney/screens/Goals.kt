package com.casey.mindmoney.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.casey.mindmoney.components.MainScaffold
import com.casey.mindmoney.navigation.NavigationRoutes
import com.casey.mindmoney.ui.theme.AppTheme
import com.casey.mindmoney.ui.theme.tertiaryLight

data class Goal(
    val title: String,
    val current: Int,
    val target: Int
)

@Composable
fun GoalsScreen(navController: NavHostController) {
    val mainGoal = Goal("Buy a House", 350000, 600000)
    val otherGoals = listOf(
        Goal("Holiday Trip", 900, 2000),
        Goal("Emergency Fund", 300, 1000),
        Goal("Laptop Upgrade", 1200, 1500)
    )
    val revenue = 2072
    val expenses = 440
    val spentOnGoals = 300
    val remaining = revenue - expenses - spentOnGoals

    MainScaffold(navController, NavigationRoutes.GOALS) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Remaining Funds Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(12.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Money to put towards goals", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "$$remaining",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "This is how much you currently have left to allocate to any savings goals.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Main Goal Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(tertiaryLight.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
                    .border(1.dp, tertiaryLight, shape = RoundedCornerShape(8.dp))
                    .padding(15.dp)
            ) {
                Text("Main Goal: ${mainGoal.title}", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                GoalProgressBar(goal = mainGoal)
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Other Goals
            Text("Other Goals", style = MaterialTheme.typography.titleMedium)
            Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                otherGoals.forEach { goal ->
                    GoalCard(goal = goal)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun GoalCard(goal: Goal) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLow, shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(goal.title, style = MaterialTheme.typography.titleMedium)

            Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                IconButton(onClick = { /* placeholder for open edit goal */ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Goal",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { /* placeholder for allocate remaining funds */ }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Allocate Funds",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(0.dp))
        GoalProgressBar(goal = goal)
    }
}

@Composable
fun GoalProgressBar(goal: Goal) {
    val progress = goal.current.toFloat() / goal.target.coerceAtLeast(1)

    val startColor = Color(0xFFFF004F)
    val endColor = Color(0xFF05EF43)
    val progressColor = lerp(startColor, endColor, progress.coerceIn(0f, 1f))

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
