package com.casey.mindmoney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.casey.mindmoney.navigation.NavigationRoutes
import com.casey.mindmoney.screens.GoalsScreen
import com.casey.mindmoney.screens.HomePageScreen
import com.casey.mindmoney.screens.RevenueExpensesScreen
import com.casey.mindmoney.screens.SuggestionsScreen
import com.casey.mindmoney.ui.theme.AppTheme
import com.casey.mindmoney.data.ViewModels.BudgetPeriodView
import com.casey.mindmoney.data.ViewModels.BudgetView

class MainActivity : ComponentActivity() {

    private val budgetPeriodViewModel: BudgetPeriodView by viewModels()
    private val budgetViewModel: BudgetView by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavigationRoutes.HOME
                    ) {
                        composable(NavigationRoutes.HOME) {
                            HomePageScreen(
                                navController = navController,
                                budgetPeriodView = budgetPeriodViewModel,
                                budgetView = budgetViewModel
                            )
                        }
                        composable(NavigationRoutes.MANAGE) {
                            RevenueExpensesScreen(
                                navController = navController,
                                budgetPeriodView = budgetPeriodViewModel
                            )
                        }
                        composable(NavigationRoutes.GOALS) {
                            GoalsScreen(navController)
                        }
                        composable(NavigationRoutes.SUGGESTIONS) {
                            SuggestionsScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
