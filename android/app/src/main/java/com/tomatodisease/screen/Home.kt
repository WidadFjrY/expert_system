package com.tomatodisease.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tomatodisease.R
import com.tomatodisease.ui.theme.Typography
import com.tomatodisease.viewModel.DiagnoseRuleViewModel
import com.tomatodisease.viewModel.HistoryViewModel
import com.tomatodisease.viewModel.MappingDataViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TomatoApp(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar() },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 26.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    Text(
                        text = "Ayo Deteksi Penyakit pada Tomat dan Temukan Solusinya",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    DiagnosisStackedQuestions(navController)
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    Text(
                        text = "Riwayat Diagnosis",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    HistoryScreen(navController = navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DiagnosisStackedQuestions(
    navController: NavController,
    viewModel: MappingDataViewModel = viewModel(),
    viewModelDiagnosis: DiagnoseRuleViewModel = viewModel())
{
    val data by viewModel.data.collectAsState()
    val questions = data.getOrNull(0)?.condition ?: emptyList()

    var currentIndex by remember { mutableIntStateOf(0) }
    var isBegin by remember { mutableStateOf(false) }

    var selectedConditions = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color(0xFFF0F0F0),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(24.dp)
            .height(260.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        if (isBegin){
            Text(
                text = "Berikan Saya ciri-ciri tanaman Anda",
                style = Typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(14.dp))
            AnimatedContent(
                targetState = currentIndex,
                transitionSpec = {
                    (slideInHorizontally(animationSpec = tween()) { fullWidth -> fullWidth } + fadeIn()) with
                            (slideOutHorizontally(animationSpec = tween()) { fullWidth -> -fullWidth } + fadeOut())
                }
                , label = ""
            ) { targetIndex ->
                if (targetIndex < questions.size) {
                    Text(
                        text = "Apakah ${questions[targetIndex].description.lowercase()}?",
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Center
                    )

                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                val context = LocalContext.current
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        val selectedId = questions[currentIndex].id
                        selectedConditions.add(selectedId)

                        if (currentIndex == questions.size - 1) {
                            viewModelDiagnosis.getRuleDiagnose(selectedConditions) { response ->
                                if (response != null) {
                                    navController.navigate("diseases/${response.ruleId}/false")
                                } else {
                                    Toast.makeText(context, "Diagnosis gagal. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                                    currentIndex = 0
                                    selectedConditions.clear()
                                    isBegin = false
                                }
                            }
                        } else {
                            currentIndex++
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06a94d))
                ) {
                    Text("Ya", style = Typography.bodyLarge, color = Color.White)
                }

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        if (currentIndex == questions.size - 1) {
                            viewModelDiagnosis.getRuleDiagnose(selectedConditions) { response ->
                                if (response != null) {
                                    navController.navigate("diseases/${response.ruleId}/false")
                                } else {
                                    Toast.makeText(context, "Diagnosis gagal. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                                    currentIndex = 0
                                    selectedConditions.clear()
                                    isBegin = false
                                }
                            }
                        } else {
                            currentIndex++
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2B2B))
                ) {
                    Text("Tidak", style = Typography.bodyLarge, color = Color.White)
                }
            }
            Button(
                onClick = {
                    currentIndex = 0
                    selectedConditions.clear()
                    isBegin = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Red
                ),
                border = BorderStroke(1.dp, Color.Red),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Reset",style = Typography.bodyLarge, color = Color.Red)
            }

        } else {
            Button(
                onClick = { isBegin = true },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF06a94d)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Diagnosa Sekarang", color = Color.White, style = Typography.bodyLarge)
            }
        }

    }
}

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = viewModel(), navController: NavController){
    val history by viewModel.history.collectAsState(initial = emptyList())

    if (history.isEmpty()){
        Text(text = "Belum ada data")
        return
    }
    Spacer(modifier = Modifier.height(12.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        history.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clickable {
                        navController.navigate("diseases/${item.ruleId}/true")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        item.disease,
                        style = Typography.titleMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        item.ruleId,
                        style = Typography.bodySmall,
                        color = Color(0xFFB3B3B3)
                    )
                    Text(
                        formatDate(item.timestamp),
                        style = Typography.bodySmall,
                        color = Color(0xFFB3B3B3)
                    )
                }

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Lihat detail",
                    tint = Color.Gray
                )
            }
        }
        Button(
            onClick = { viewModel.deleteAllHistory() },
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2B2B)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Hapus Semua Riwayat", color = Color.White, style = Typography.bodyLarge,)
        }
    }
}

@Composable
fun TopAppBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF06a94d))
            .padding(top = 42.dp, start = 26.dp, end = 26.dp, bottom = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Halo, Petani 👋", style = Typography.titleLarge, color = Color.White)
                Text(
                    text = "Sistem Pakar",
                    fontSize = 20.sp,
                    color = Color(0xFFF3F3F3)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.height(50.dp)
            )
        }
    }
}


fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
    return sdf.format(Date(timestamp))
}