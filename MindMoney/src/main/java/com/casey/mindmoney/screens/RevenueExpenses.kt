package com.casey.mindmoney.screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.casey.mindmoney.data.ViewModels.BudgetPeriodView

@Composable
fun RevenueExpensesScreen(
    navController: NavHostController,
    budgetPeriodView: BudgetPeriodView
) {
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
    val footerHeight = 64.dp

    val gradientColors = if (isLight) {
        listOf(Color(0xFFDFF5E1), Color(0xFFFFE5E5), Color(0xFFF3E5F5))
    } else {
        listOf(Color(0xFF1E2D1E), Color(0xFF2E1F1F), Color(0xFF2A1F2E))
    }

    MainScaffold(navController, NavigationRoutes.MANAGE) { paddingValues ->

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
                val revenueTotalFormatted = "$${"%.2f".format(revenueTotal)}"
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SectionHeader(
                        title = "Revenue",
                        total = revenueTotalFormatted,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        textColor = Color.White
                    )

                    SectionBox(title = "Revenue", borderColor = tertiaryLight) {
                        incomeList.forEach { transaction ->
                            Text("• ${transaction.category}: \$${transaction.amount}")
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    addType = "Income"
                                    showRevenueDialog = true
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Revenue",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Add new income", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                // Expenses section
                val expenseTotalFormatted = "$${"%.2f".format(expenseTotal)}"
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SectionHeader(
                        title = "Expenses",
                        total = expenseTotalFormatted,
                        backgroundColor = MaterialTheme.colorScheme.error,
                        textColor = Color.White
                    )

                    SectionBox(title = "Expenses", borderColor = errorLight) {
                        expenseList.forEach { transaction ->
                            Text("• ${transaction.category}: \$${transaction.amount}")
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    addType = "expense"
                                    showExpenseDialog = true
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Expense",
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Add new expense", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }

                // Goals section
                val goalFormatted = "$${"%.2f".format(goalSpending)}"
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SectionHeader(
                        title = "Goals",
                        total = goalFormatted,
                        backgroundColor = Color(0xFF8E24AA),
                        textColor = Color.White
                    )

                    SectionBox(title = "Goals", borderColor = Color(0xFF8E24AA)) {
                        Text("• Goal Spending This Period: $goalFormatted")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "This value represents the total spent toward active goals this period.\nMore details can be found on the Goals page.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "→ View Goals Page",
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            modifier = Modifier.clickable {
                                navController.navigate(NavigationRoutes.GOALS)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(footerHeight + 16.dp))

                // === Pop-up Dialog Section ===
                if (showRevenueDialog) {
                    AddTransactionDialog(
                        title = addType,
                        type = addType,
                        onDismiss = { showRevenueDialog = false },
                        onSave = { amount, category, note, date, type ->
                            transactionViewModel.addTransaction(
                                TransactionEnt(amount = amount, type = type, category = category, note = note, date = date)
                            )
                            showRevenueDialog = false
                        }
                    )
                }

                if (showExpenseDialog) {
                    AddTransactionDialog(
                        title = "Add Expense",
                        type = "expense",
                        onDismiss = { showExpenseDialog = false },
                        onSave = { amount, category, note, date, type ->
                            transactionViewModel.addTransaction(
                                TransactionEnt(amount = amount, type = type, category = category, note = note, date = date)
                            )
                            showExpenseDialog = false
                        }
                    )
                }
            }

            // === Sticky Footer ===
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
                        color = MaterialTheme.colorScheme.onSecondaryContainer
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

@Composable
fun SectionHeader(
    title: String,
    total: String,
    backgroundColor: Color,
    textColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = backgroundColor,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = title,
                color = textColor,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center
            )
            Text(
                text = total,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(name = "Preview Mode", showBackground = true, showSystemUi = true)
@Composable
fun RevenueExpensesScreenPreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        val dummyNavController = rememberNavController()
        val fakeBudgetPeriodView = remember {
            BudgetPeriodView(Application())
        }

        RevenueExpensesScreen(
            navController = dummyNavController,
            budgetPeriodView = fakeBudgetPeriodView
        )
    }
}

