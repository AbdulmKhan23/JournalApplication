package com.khan.journalapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.khan.journalapplication.R

val Lora = FontFamily(
    Font(R.font.lora_regular, FontWeight.Normal),
    Font(R.font.lora_medium, FontWeight.Medium),
    Font(R.font.lora_semibold, FontWeight.SemiBold),
    Font(R.font.lora_bold, FontWeight.Bold),
    Font(R.font.lora_italic, FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.lora_mediumitalic, FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.lora_semibolditalic, FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.lora_bolditalic, FontWeight.Bold, style = FontStyle.Italic)
)

private fun TextStyle.withLora() = copy(fontFamily = Lora)

// Keep Material 3's standard sizes and spacing while applying Lora everywhere.
private val DefaultTypography = Typography()
val Typography = Typography(
    displayLarge = DefaultTypography.displayLarge.withLora(),
    displayMedium = DefaultTypography.displayMedium.withLora(),
    displaySmall = DefaultTypography.displaySmall.withLora(),
    headlineLarge = DefaultTypography.headlineLarge.withLora(),
    headlineMedium = DefaultTypography.headlineMedium.withLora(),
    headlineSmall = DefaultTypography.headlineSmall.withLora(),
    titleLarge = DefaultTypography.titleLarge.withLora(),
    titleMedium = DefaultTypography.titleMedium.withLora(),
    titleSmall = DefaultTypography.titleSmall.withLora(),
    bodyLarge = DefaultTypography.bodyLarge.withLora(),
    bodyMedium = DefaultTypography.bodyMedium.withLora(),
    bodySmall = DefaultTypography.bodySmall.withLora(),
    labelLarge = DefaultTypography.labelLarge.withLora(),
    labelMedium = DefaultTypography.labelMedium.withLora(),
    labelSmall = DefaultTypography.labelSmall.withLora()
)
