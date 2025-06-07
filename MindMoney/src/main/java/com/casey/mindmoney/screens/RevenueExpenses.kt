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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PsychologyAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.casey.mindmoney.ui.theme.errorLight
import com.casey.mindmoney.ui.theme.tertiaryLight

@Composable
fun RevenueExpensesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.PsychologyAlt, contentDescription = "Logo", tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("MindMoney", style = MaterialTheme.typography.titleLarge)
            }
            Text("User ▼", style = MaterialTheme.typography.bodyMedium)
        }

        // Revenue section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, tertiaryLight, shape = MaterialTheme.shapes.medium)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Revenue", style = MaterialTheme.typography.titleMedium)
                Icon(Icons.Default.Add, contentDescription = "Add Revenue", tint = MaterialTheme.colorScheme.primary)
            }

            Text("• Wage: $1970", style = MaterialTheme.typography.bodyLarge)
            Text("• Stocks: $102", style = MaterialTheme.typography.bodyLarge)
        }

        // Expenses section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, errorLight, shape = MaterialTheme.shapes.medium)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Expenses", style = MaterialTheme.typography.titleMedium)
                Icon(Icons.Default.Add, contentDescription = "Add Expense", tint = MaterialTheme.colorScheme.primary)
            }

            Text("• Groceries: $240", style = MaterialTheme.typography.bodyLarge)
            Text("• Utilities: $120", style = MaterialTheme.typography.bodyLarge)
            Text("• Transport: $80", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
