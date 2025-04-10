package com.tomatodisease.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Database
import com.tomatodisease.model.DiagnosisEntity
import com.tomatodisease.service.AppDatabase
import com.tomatodisease.service.NetworkImage
import com.tomatodisease.viewModel.GetDiagnoseViewModel
import com.tomatodisease.ui.theme.Typography
import com.tomatodisease.viewModel.HistoryViewModel

@Composable
fun DiseasesScreen(
    navController: NavController,
    ruleId: String?,
    isFromHome: Boolean?,
    viewModel:GetDiagnoseViewModel = viewModel(),
    viewModelHistory: HistoryViewModel = viewModel(),
    database: AppDatabase
){
    LaunchedEffect(Unit) {
        if (ruleId != null) {
            viewModel.getDiagnose(ruleId)
        }
    }
    
    val diagnoseState by viewModel.diagnoseGetResponse.collectAsState()

    if (!isFromHome!!){
        val dao = database.diagnosisDao()
        LaunchedEffect(diagnoseState) {
            diagnoseState?.let { data ->
                val entity = DiagnosisEntity(
                    ruleId = data.ruleId,
                    disease = data.disease,
                    matchedConditions = data.matchedConditions.joinToString(", "),
                    solutions = data.solution.joinToString(", ")
                )

                viewModelHistory.saveDiagnosisToLocal(entity, dao)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MyTopBar(title = "Hasil Diagnosa", navController = navController) }

    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .padding(26.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                diagnoseState?.let { data ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            Text(data.disease, style = Typography.titleLarge)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        item {
                            NetworkImage(fileName = data.ruleId)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        item {
                            Text(
                                text = "Ciri-ciri",
                                style = Typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        itemsIndexed(data.matchedConditions) { index, condition ->
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text("${index + 1}.", modifier = Modifier.width(15.dp))
                                Text(condition)
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Solusi",
                                style = Typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        itemsIndexed(data.solution) { index, solution ->
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text("${index + 1}.", modifier = Modifier.width(15.dp))
                                Text(solution)
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(title: String, navController: NavController) {
    TopAppBar(
        title = {
            Text(text = title, color = Color.White, fontSize = 18.sp)
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF06a94d),
            titleContentColor = Color.White
        )
    )
}