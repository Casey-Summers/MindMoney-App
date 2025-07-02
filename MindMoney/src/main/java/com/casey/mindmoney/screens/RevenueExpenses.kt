package com.casey.mindmoney.screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.casey.mindmoney.components.AddTransactionDialog
import com.casey.mindmoney.components.MainScaffold
import com.casey.mindmoney.data.Entities.TransactionEnt
import com.casey.mindmoney.data.ViewModels.BudgetPeriodView
import com.casey.mindmoney.data.ViewModels.BudgetView
import com.casey.mindmoney.data.ViewModels.TransactionViewModel
import com.casey.mindmoney.navigation.NavigationRoutes
import com.casey.mindmoney.ui.theme.AppTheme
import com.casey.mindmoney.ui.theme.errorLight
import com.casey.mindmoney.ui.theme.tertiaryLight

@Composable
fun RevenueExpensesScreen(
    navController: NavHostController,
    budgetPeriodView: BudgetPeriodView,
    budgetView: BudgetView
) {
    val transactionViewModel: TransactionViewModel = viewModel()

    val incomeList by transactionViewModel.incomeTransactions.collectAsState()
    val oneOffList by transactionViewModel.oneOffExpenses.collectAsState()
    val recurringList by transactionViewModel.recurringExpenses.collectAsState()

    var showRevenueDialog by remember { mutableStateOf(false) }
    var showOneOffDialog by remember { mutableStateOf(false) }
    var showRecurringDialog by remember { mutableStateOf(false) }

    val budget by budgetView.budget.collectAsStateWithLifecycle()
    val goalSpending = 300.0
    val remaining = incomeList.sumOf { it.amount } -
            (oneOffList.sumOf { it.amount } + recurringList.sumOf { it.amount } + goalSpending)

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
                .background(Brush.verticalGradient(gradientColors, 0f, 2000f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Revenue
                SectionTemplate(
                    title = "Revenue",
                    total = formatCurrency(incomeList.sumOf { it.amount }),
                    budget = null,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    borderColor = tertiaryLight,
                    items = incomeList,
                    addLabel = "Add new income",
                    onAddClicked = { showRevenueDialog = true }
                )

                // One-off Expenses
                SectionTemplate(
                    title = "One-off Expenses",
                    total = formatCurrency(oneOffList.sumOf { it.amount }),
                    budget = budget.oneOff,
                    backgroundColor = MaterialTheme.colorScheme.error,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    borderColor = errorLight,
                    items = oneOffList,
                    addLabel = "Add one-off expense",
                    onAddClicked = { showOneOffDialog = true }
                )

                // Recurring Expenses
                SectionTemplate(
                    title = "Recurring Expenses",
                    total = formatCurrency(recurringList.sumOf { it.amount }),
                    budget = budget.recurring,
                    backgroundColor = Color(0xFFFFCC80),
                    textColor = Color.Black,
                    borderColor = Color(0xFFFFCC80),
                    items = recurringList,
                    addLabel = "Add recurring expense",
                    onAddClicked = { showRecurringDialog = true }
                )

                // Goals
                GoalsSection(goalSpending, budget.goals) {
                    navController.navigate(NavigationRoutes.GOALS)
                }

                Spacer(Modifier.height(footerHeight + 16.dp))

                // Dialogs
                TransactionDialogIfNeeded(showRevenueDialog, { showRevenueDialog = it }, "Income", transactionViewModel)
                TransactionDialogIfNeeded(showOneOffDialog, { showOneOffDialog = it }, "expense", transactionViewModel, "one-off")
                TransactionDialogIfNeeded(showRecurringDialog, { showRecurringDialog = it }, "expense", transactionViewModel, "recurring")
            }

            // Sticky Footer
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
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Remaining Money:", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        Text("${formatCurrency(remaining)} / ${formatCurrency(budget.leftOver)}", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
            }
        }
    }
}

@Composable
fun SectionTemplate(
    title: String,
    total: String,
    budget: Double?,
    backgroundColor: Color,
    textColor: Color,
    borderColor: Color,
    items: List<TransactionEnt>,
    addLabel: String,
    onAddClicked: () -> Unit
) {
    val totalDisplay = if (budget != null) "$total / ${formatCurrency(budget)}" else total
    Column {
        SectionHeader(title, totalDisplay, backgroundColor, textColor)
        SectionBox(title, borderColor) {
            items.forEach { Text("• ${it.category}: $${it.amount}") }
            Spacer(Modifier.height(2.dp))
            AddTransactionRow(addLabel, backgroundColor, onAddClicked)
        }
    }
}

@Composable
fun GoalsSection(total: Double, budget: Double, onClick: () -> Unit) {
    val formatted = "${formatCurrency(total)} / ${formatCurrency(budget)}"
    Column {
        SectionHeader("Goals", formatted, Color(0xFFE5A7FF), MaterialTheme.colorScheme.onPrimary)
        SectionBox("Goals", Color(0xFFE5A7FF)) {
            Text("• Goal Spending This Period: $formatted")
            Spacer(Modifier.height(4.dp))
            Text("This value represents the total spent toward active goals this period. More details can be found on the Goals page.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(4.dp))
            Text("→ View Goals Page", color = MaterialTheme.colorScheme.tertiary, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium), modifier = Modifier.clickable { onClick() })
        }
    }
}

@Composable
fun AddTransactionRow(label: String, color: Color, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { onClick() }, verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Add, contentDescription = null, tint = color)
        Spacer(Modifier.width(8.dp))
        Text(label, color = color)
    }
}

@Composable
fun SectionBox(title: String, borderColor: Color, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface, RectangleShape).border(1.dp, borderColor, RectangleShape).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = content
    )
}

@Composable
fun SectionHeader(title: String, total: String, backgroundColor: Color, textColor: Color) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = backgroundColor,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        tonalElevation = 2.dp
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(backgroundColor).padding(vertical = 6.dp, horizontal = 8.dp)) {
            Text(text = total, style = MaterialTheme.typography.bodyMedium, color = textColor, modifier = Modifier.align(Alignment.CenterEnd))
            Text(text = title, style = MaterialTheme.typography.titleMedium, color = textColor, modifier = Modifier.align(Alignment.CenterStart))
        }
    }
}

@Composable
fun TransactionDialogIfNeeded(showDialog: Boolean, setShowDialog: (Boolean) -> Unit, addType: String, transactionViewModel: TransactionViewModel, expenseType: String? = null) {
    if (showDialog) {
        AddTransactionDialog(
            title = addType,
            type = addType,
            onDismiss = { setShowDialog(false) },
            onSave = { amount, category, note, date, type ->
                transactionViewModel.addTransaction(TransactionEnt(amount = amount, type = type, category = category, note = note, date = date, expenseType = expenseType))
                setShowDialog(false)
            }
        )
    }
}

@Composable fun formatCurrency(amount: Double): String = "$${"%.2f".format(amount)}"

@Preview(name = "Preview Mode", showBackground = true, showSystemUi = true)
@Composable
fun RevenueExpensesScreenPreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        val dummyNavController = rememberNavController()
        val fakeBudgetPeriodView = remember { BudgetPeriodView(Application()) }
        val fakeBudgetView = remember { BudgetView(Application()) }
        RevenueExpensesScreen(dummyNavController, fakeBudgetPeriodView, fakeBudgetView)
    }
}