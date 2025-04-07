package com.tomatodisease.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tomatodisease.R

val Poppins = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_bold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        fontSize = 16.sp,
        color = Color(0xFF2E2E2E)
    ),
    titleLarge = TextStyle(
        fontFamily = Poppins,
        fontSize = 30.sp,
        color = Color(0xFF2F2F2F),
        fontWeight = FontWeight.Bold,
    ),
    titleMedium  = TextStyle(
        fontFamily = Poppins,
        fontSize = 20.sp,
        color = Color(0xFF2F2F2F),
        fontWeight = FontWeight.Bold,
    ),
)