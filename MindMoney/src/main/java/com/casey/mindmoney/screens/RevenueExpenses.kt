package com.casey.mindmoney.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.casey.mindmoney.components.AddTransactionDialog
import com.casey.mindmoney.components.MainScaffold
import com.casey.mindmoney.data.Entities.TransactionEnt
import com.casey.mindmoney.data.ViewModels.TransactionViewModel
import com.casey.mindmoney.navigation.NavigationRoutes
import com.casey.mindmoney.ui.theme.AppTheme
import com.casey.mindmoney.ui.theme.errorLight
import com.casey.mindmoney.ui.theme.tertiaryLight

@Composable
fun RevenueExpensesScreen(navController: NavHostController) {
    val transactionViewModel: TransactionViewModel = viewModel()
    val incomeList = transactionViewModel.incomeTransactions.collectAsState().value
    val expenseList = transactionViewModel.expenseTransactions.collectAsState().value

    var showRevenueDialog by remember { mutableStateOf(false) }
    var showExpenseDialog by remember { mutableStateOf(false) }
    var addType by remember { mutableStateOf("Income") }

    val revenueTotal = incomeList.sumOf { it.amount }
    val expenseTotal = expenseList.sumOf { it.amount }
    val goalSpending = 300.0
    val remaining = revenueTotal - expenseTotal - goalSpending

    val isLight = !isSystemInDarkTheme()

    val gradientColors = if (isLight) {
        listOf(
            Color(0xFFDFF5E1), // light green
            Color(0xFFFFE5E5), // light red
            Color(0xFFF3E5F5)  // light purple
        )
    } else {
        listOf(
            Color(0xFF1E2D1E), // dark green
            Color(0xFF2E1F1F), // dark red
            Color(0xFF2A1F2E)  // dark purple
        )
    }

    MainScaffold(navController, NavigationRoutes.MANAGE) { paddingValues ->
        val footerHeight = 64.dp

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = gradientColors,
                        startY = 0f,
                        endY = 2000f
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 10.dp),
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
                                showRevenueDialog = true
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
                                addType = "expense"
                                showExpenseDialog = true
                            }
                        )
                    }
                    expenseList.forEach { transaction ->
                        Text("• ${transaction.category}: \$${transaction.amount}")
                    }
                }

                // Goals section
                SectionBox(title = "Goals", borderColor = Color(0xFF8E24AA)) {
                    Text("• Goal Spending: \$${goalSpending}")
                }

                Spacer(modifier = Modifier.height(footerHeight + 16.dp))

                // === Pop-up Dialog Section ===
                // Pop-up Dialog for adding Revenue
                if (showRevenueDialog) {
                    AddTransactionDialog(
                        title = addType,
                        type = addType,
                        onDismiss = { showRevenueDialog = false },
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
                            showRevenueDialog = false
                        }
                    )
                }

                // Pop-up Dialog for adding Expense
                if (showExpenseDialog) {
                    AddTransactionDialog(
                        title = "Add Expense",
                        type = "expense",
                        onDismiss = { showExpenseDialog = false },
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
                            showExpenseDialog = false
                        }
                    )
                }
            }

            // === Sticky Footer ===
            // Remaining Money section
            Surface(
                tonalElevation = 4.dp,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(footerHeight)
                    .align(Alignment.BottomCenter)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        "Remaining Money: \$${"%.2f".format(remaining)}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun SectionBox(title: String, borderColor: Color, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RectangleShape)
            .border(1.dp, borderColor, RectangleShape)
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
