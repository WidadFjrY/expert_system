package com.tomatodisease

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomatodisease.ui.theme.TomatoDiseaseTheme
import com.tomatodisease.ui.theme.Typography
import androidx.compose.animation.*
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.tomatodisease.screen.DiseasesScreen
import com.tomatodisease.screen.TomatoApp
import com.tomatodisease.service.AppDatabase
import java.util.concurrent.Executors


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TomatoDiseaseTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = remember {
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "diagnosis_db"
        )
            .setQueryCallback({ sqlQuery, bindArgs ->
                Log.d("RoomQuery", "SQL: $sqlQuery | Args: $bindArgs")
            }, Executors.newSingleThreadExecutor())
            .build()
    }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            TomatoApp(navController)
        }
        composable(
            route = "diseases/{ruleId}/{isFromHome}",
            arguments = listOf(
                navArgument("ruleId") { type = NavType.StringType },
                navArgument("isFromHome") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val ruleId = backStackEntry.arguments?.getString("ruleId")
            val isFromHome = backStackEntry.arguments?.getString("isFromHome")?.toBoolean() ?: false

            DiseasesScreen(
                navController = navController,
                ruleId = ruleId,
                database = database,
                isFromHome = isFromHome
            )
        }
    }
}


