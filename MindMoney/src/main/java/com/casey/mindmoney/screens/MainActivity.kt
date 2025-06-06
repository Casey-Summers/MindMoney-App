package com.casey.mindmoney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.casey.mindmoney.screens.HomePageScreen
import com.casey.mindmoney.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomePageScreen()
                }
            }
        }
    }
}

@Preview(name = "Preview Mode", showBackground = true, showSystemUi = true)
@Composable
fun HomePageLightPreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        HomePageScreen()
    }
}
