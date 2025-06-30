package com.casey.mindmoney.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.casey.mindmoney.components.MainScaffold
import com.casey.mindmoney.data.ViewModels.TransactionViewModel
import com.casey.mindmoney.navigation.NavigationRoutes
import com.casey.mindmoney.ui.theme.AppTheme
import com.casey.mindmoney.ui.theme.errorLight
import com.casey.mindmoney.ui.theme.tertiaryLight
import androidx.compose.foundation.clickable
import com.casey.mindmoney.components.AddTransactionDialog
import com.casey.mindmoney.data.Entities.TransactionEnt


@Composable
fun RevenueExpensesScreen(navController: NavHostController) {
    val transactionViewModel: TransactionViewModel = viewModel()
    val incomeList = transactionViewModel.incomeTransactions.collectAsState().value
    val expenseList = transactionViewModel.expenseTransactions.collectAsState().value

    var showAddDialog by remember { mutableStateOf(false) }
    var addType by remember { mutableStateOf("Income") }

    val revenueTotal = incomeList.sumOf { it.amount }
    val expenseTotal = expenseList.sumOf { it.amount }
    val goalSpending = 300.0
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
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Revenue",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            addType = "Income"
                            showAddDialog = true
                        }
                    )
                }
                incomeList.forEach { transaction ->
                    Text("• ${transaction.category}: \$${transaction.amount}")
                }
            }

            // Expenses section
            SectionBox(title = "Expenses", borderColor = errorLight) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Expenses", style = MaterialTheme.typography.titleMedium)
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Revenue",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            addType = "Income"
                            showAddDialog = true
                        }
                    )
                }
                expenseList.forEach { transaction ->
                    Text("• ${transaction.category}: \$${transaction.amount}")
                }
            }

            // Goals section (NEW)
            SectionBox(title = "Goals", borderColor = Color(0xFF8E24AA)) {
                Text("• Goal Spending: \$${goalSpending}")
            }

            // Remaining section (NEW)
            HorizontalDivider()
            Text(
                "Remaining Money: \$${"%.2f".format(remaining)}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (showAddDialog) {
                AddTransactionDialog(
                    title = addType,
                    type = addType,
                    onDismiss = { showAddDialog = false },
                    onSave = { amount, category, note, date, type ->
                        transactionViewModel.addTransaction(
                            TransactionEnt(
                                amount = amount,
                                type = type,
                                category = category,
                                note = note,
                                date = date
                            )
                        )
                        showAddDialog = false
                    }
                )
            }
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
