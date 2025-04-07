package com.tomatodisease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TomatoDiseaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TomatoApp(modifier = Modifier
                        .padding(innerPadding)
                        .padding(26.dp)
                        .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun TomatoApp(modifier: Modifier){
    Surface(
        modifier = modifier
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Halo, Petani 👋", style = Typography.titleLarge)
                    Text(text = "Ayo Kenali Penyakit",
                        fontSize = 20.sp,
                        color = Color(0xFF888888)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Sistem Pakar Deteksi Penyakit Tomat",
                style = Typography.titleMedium,
                textAlign = TextAlign.Center
                )
            Spacer(modifier = Modifier.height(24.dp))
            DiagnosisStackedQuestions()
        }
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DiagnosisStackedQuestions() {
    val questions = listOf(
        "Apakah daun menguning?",
        "Apakah ada bintik hitam?",
        "Apakah batang tanaman busuk?"
    )
    var currentIndex by remember { mutableIntStateOf(0) }
    var isBegin by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        if (isBegin){
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
                        text = questions[targetIndex],
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
                    .padding(horizontal = 16.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (currentIndex < questions.size - 1) currentIndex++
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF08F26E))
                ) {
                    Text("Ya", style = Typography.bodyLarge, color = Color.White)
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (currentIndex < questions.size - 1) currentIndex++
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2B2B))
                ) {
                    Text("Tidak", style = Typography.bodyLarge, color = Color.White)
                }
            }
        } else {
            Button(
                onClick = { isBegin = true },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF08F26E)
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

