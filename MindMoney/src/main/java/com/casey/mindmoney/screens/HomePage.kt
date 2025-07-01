package com.casey.mindmoney.screens

import android.app.Application
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.casey.mindmoney.components.AllowanceField
import com.casey.mindmoney.components.MainScaffold
import com.casey.mindmoney.components.PieChartWithTotal
import com.casey.mindmoney.data.ViewModels.BudgetPeriodView
import com.casey.mindmoney.navigation.NavigationRoutes
import com.casey.mindmoney.ui.theme.AppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.casey.mindmoney.data.ViewModels.BudgetView
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction


@Composable
fun HomePageScreen(
    navController: NavHostController,
    budgetPeriodView: BudgetPeriodView,
    budgetView: BudgetView
) {
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

            // Budgets per defined period
            val currentPeriod by budgetPeriodView.period.collectAsStateWithLifecycle()

            Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = {
                            val next = when (currentPeriod) {
                                "Weekly" -> "Fortnightly"
                                "Fortnightly" -> "Monthly"
                                else -> "Weekly"
                            }
                            budgetPeriodView.setPeriod(next)
                        },
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text(
                            text = currentPeriod,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Budgets", style = MaterialTheme.typography.titleMedium)

                    var showInfoDialog by remember { mutableStateOf(false) }

                    TextButton(onClick = { showInfoDialog = true }) {
                        Text("Not sure? Learn more here.", style = MaterialTheme.typography.bodySmall)
                    }

                    if (showInfoDialog) {
                        AlertDialog(
                            onDismissRequest = { showInfoDialog = false },
                            title = { Text("About Budgeting Periods") },
                            text = {
                                Text("Setting your budget defines when your one-off expenses will reset. " +
                                        "It’s recommended you match this with your pay cycle. Use the fields below to estimate how much " +
                                        "you intend to spend on each category per period.")
                            },
                            confirmButton = {
                                TextButton(onClick = { showInfoDialog = false }) {
                                    Text("Close")
                                }
                            }
                        )
                    }
                }
            }

            val budget by budgetView.budget.collectAsStateWithLifecycle()

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BudgetInputField("One-off Expenses", budget.oneOff) {
                    budgetView.updateField("oneOff", it)
                }
                BudgetInputField("Recurring Expenses", budget.recurring) {
                    budgetView.updateField("recurring", it)
                }
                BudgetInputField("Goals", budget.goals) {
                    budgetView.updateField("goals", it)
                }
                BudgetInputField("Left-over Saving", budget.leftOver) {
                    budgetView.updateField("leftOver", it)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
fun BudgetInputField(
    label: String,
    value: Double,
    onValueChange: (Double) -> Unit
) {
    var input by remember { mutableStateOf(value.toString()) }
    var isSelected by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = input,
        onValueChange = {
            input = it
            it.toDoubleOrNull()?.let { parsed -> onValueChange(parsed) }
        },
        label = { Text(label) },
        leadingIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("$", modifier = Modifier.padding(end = 2.dp))
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isSelected = !isSelected },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(letterSpacing = 0.5.sp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {
                isSelected = false
                // Optionally move focus to next field programmatically
            }
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePagePreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        val dummyNavController = rememberNavController()
        val fakeBudgetPeriodView = remember { BudgetPeriodView(Application()) }
        val fakeBudgetView = remember { BudgetView(Application()) }

        HomePageScreen(
            navController = dummyNavController,
            budgetPeriodView = fakeBudgetPeriodView,
            budgetView = fakeBudgetView
        )
    }
}

