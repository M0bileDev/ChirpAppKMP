package com.example.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

// Brand colors
val ChirpBrand1000 = Color(0xFF092E2D)
val ChirpBrand900 = Color(0xFF106461)
val ChirpBrand600 = Color(0xFF19BA87)
val ChirpBrand500 = Color(0xFF4DDEAB)
val ChirpBrand500Alpha40 = Color(0x664DDEAB)
val ChirpBrand100 = Color(0xFFCEF5E4)

// Base Colors
val ChirpBase1000 = Color(0xFF101C28)
val ChirpBase1000Alpha8 = Color(0x14101C28)
val ChirpBase1000Alpha80 = Color(0xCC101C28)
val ChirpBase950 = Color(0xFF1C2A39)
val ChirpBase900 = Color(0xFF2F3F4F)
val ChirpBase800 = Color(0xFF475767)
val ChirpBase700 = Color(0xFF667685)
val ChirpBase500 = Color(0xFF8597A9)
val ChirpBase400 = Color(0xFF9DADBE)
val ChirpBase200 = Color(0xFFD0D7DD)
val ChirpBase150 = Color(0xFFDCE0E5)
val ChirpBase100 = Color(0xFFF5F7F8)
val ChirpBase100Alpha10 = Color(0x1AF5F7F8)
val ChirpBase1000Alpha14 = Color(0x14101C28) // 8% alpha for light mode surface outline
val ChirpBase100Alpha10Alt = Color(0x1AF5F7F8) // 10% alpha for dark mode surface outline
val ChirpBase0 = Color(0xFFFFFFFF)

// Red Colors
val ChirpRed600 = Color(0xFFAA142A)
val ChirpRed500 = Color(0xFFDA233E)
val ChirpRed200 = Color(0xFFFF7987)

// Accent Colors (15% alpha)
val ChirpBlue = Color(0x26A2C0FF)
val ChirpPurple = Color(0x26CAADFF)
val ChirpViolet = Color(0x26FCB6FF)
val ChirpPink = Color(0x26FFA6AF)
val ChirpOrange = Color(0x26FEC5A7)
val ChirpYellow = Color(0x26FEF4A5)
val ChirpGreen = Color(0x26C2FFA6)
val ChirpTeal = Color(0x26A0FFE0)
val ChirpLightBlue = Color(0x2698E4FF)
val ChirpGrey = Color(0x26D0D7DD)

// Cake Colors - Light Theme (Soft pastel shades with good contrast)
val ChirpCakeLightViolet = Color(0xFFF0D9FF) // Soft violet
val ChirpCakeLightGreen = Color(0xFFDEFFD9) // Soft green
val ChirpCakeLightBlue = Color(0xFFD9E8FF) // Soft blue
val ChirpCakeLightPink = Color(0xFFFFE0E6) // Soft pink
val ChirpCakeLightOrange = Color(0xFFFFE8D9) // Soft orange
val ChirpCakeLightYellow = Color(0xFFFFF9D9) // Soft yellow
val ChirpCakeLightTeal = Color(0xFFD9FFF2) // Soft teal
val ChirpCakeLightPurple = Color(0xFFE6D9FF) // Soft purple
val ChirpCakeLightRed = Color(0xFFFFD9DC) // Soft red
val ChirpCakeLightMint = Color(0xFFE0FFE8) // Soft mint

// Cake Colors - Dark Theme (Muted shades with good contrast)
val ChirpCakeDarkViolet = Color(0x26FCB6FF) // Muted violet (15% alpha)
val ChirpCakeDarkGreen = Color(0x26C2FFA6) // Muted green (15% alpha)
val ChirpCakeDarkBlue = Color(0x26A2C0FF) // Muted blue (15% alpha)
val ChirpCakeDarkPink = Color(0x26FFA6AF) // Muted pink (15% alpha)
val ChirpCakeDarkOrange = Color(0x26FEC5A7) // Muted orange (15% alpha)
val ChirpCakeDarkYellow = Color(0x26FEF4A5) // Muted yellow (15% alpha)
val ChirpCakeDarkTeal = Color(0x26A0FFE0) // Muted teal (15% alpha)
val ChirpCakeDarkPurple = Color(0x26CAADFF) // Muted purple (15% alpha)
val ChirpCakeDarkRed = Color(0x26FF8A95) // Muted red (15% alpha)
val ChirpCakeDarkMint = Color(0x26A6FFCC) // Muted mint (15% alpha)

@Immutable
data class ExtendedColors(
    // Button states
    val primaryHover: Color,
    val destructiveHover: Color,
    val destructiveSecondaryOutline: Color,
    val disabledOutline: Color,
    val disabledFill: Color,
    val successOutline: Color,
    val success: Color,
    val onSuccess: Color,
    val secondaryFill: Color,

    // Text variants
    val textPrimary: Color,
    val textTertiary: Color,
    val textSecondary: Color,
    val textPlaceholder: Color,
    val textDisabled: Color,

    // Surface variants
    val surfaceLower: Color,
    val surfaceHigher: Color,
    val surfaceOutline: Color,
    val overlay: Color,

    // Accent colors
    val accentBlue: Color,
    val accentPurple: Color,
    val accentViolet: Color,
    val accentPink: Color,
    val accentOrange: Color,
    val accentYellow: Color,
    val accentGreen: Color,
    val accentTeal: Color,
    val accentLightBlue: Color,
    val accentGrey: Color,

    // Cake colors for chat bubbles
    val cakeViolet: Color,
    val cakeGreen: Color,
    val cakeBlue: Color,
    val cakePink: Color,
    val cakeOrange: Color,
    val cakeYellow: Color,
    val cakeTeal: Color,
    val cakePurple: Color,
    val cakeRed: Color,
    val cakeMint: Color,
)

val LightExtendedColors = ExtendedColors(
    primaryHover = ChirpBrand600,
    destructiveHover = ChirpRed600,
    destructiveSecondaryOutline = ChirpRed200,
    disabledOutline = ChirpBase200,
    disabledFill = ChirpBase150,
    successOutline = ChirpBrand100,
    success = ChirpBrand600,
    onSuccess = ChirpBase0,
    secondaryFill = ChirpBase100,

    textPrimary = ChirpBase1000,
    textTertiary = ChirpBase800,
    textSecondary = ChirpBase900,
    textPlaceholder = ChirpBase700,
    textDisabled = ChirpBase400,

    surfaceLower = ChirpBase100,
    surfaceHigher = ChirpBase100,
    surfaceOutline = ChirpBase1000Alpha14,
    overlay = ChirpBase1000Alpha80,

    accentBlue = ChirpBlue,
    accentPurple = ChirpPurple,
    accentViolet = ChirpViolet,
    accentPink = ChirpPink,
    accentOrange = ChirpOrange,
    accentYellow = ChirpYellow,
    accentGreen = ChirpGreen,
    accentTeal = ChirpTeal,
    accentLightBlue = ChirpLightBlue,
    accentGrey = ChirpGrey,

    cakeViolet = ChirpCakeLightViolet,
    cakeGreen = ChirpCakeLightGreen,
    cakeBlue = ChirpCakeLightBlue,
    cakePink = ChirpCakeLightPink,
    cakeOrange = ChirpCakeLightOrange,
    cakeYellow = ChirpCakeLightYellow,
    cakeTeal = ChirpCakeLightTeal,
    cakePurple = ChirpCakeLightPurple,
    cakeRed = ChirpCakeLightRed,
    cakeMint = ChirpCakeLightMint,
)

val DarkExtendedColors = ExtendedColors(
    primaryHover = ChirpBrand600,
    destructiveHover = ChirpRed600,
    destructiveSecondaryOutline = ChirpRed200,
    disabledOutline = ChirpBase900,
    disabledFill = ChirpBase1000,
    successOutline = ChirpBrand500Alpha40,
    success = ChirpBrand500,
    onSuccess = ChirpBase1000,
    secondaryFill = ChirpBase900,

    textPrimary = ChirpBase0,
    textTertiary = ChirpBase200,
    textSecondary = ChirpBase150,
    textPlaceholder = ChirpBase400,
    textDisabled = ChirpBase500,

    surfaceLower = ChirpBase1000,
    surfaceHigher = ChirpBase900,
    surfaceOutline = ChirpBase100Alpha10Alt,
    overlay = ChirpBase1000Alpha80,

    accentBlue = ChirpBlue,
    accentPurple = ChirpPurple,
    accentViolet = ChirpViolet,
    accentPink = ChirpPink,
    accentOrange = ChirpOrange,
    accentYellow = ChirpYellow,
    accentGreen = ChirpGreen,
    accentTeal = ChirpTeal,
    accentLightBlue = ChirpLightBlue,
    accentGrey = ChirpGrey,

    cakeViolet = ChirpCakeDarkViolet,
    cakeGreen = ChirpCakeDarkGreen,
    cakeBlue = ChirpCakeDarkBlue,
    cakePink = ChirpCakeDarkPink,
    cakeOrange = ChirpCakeDarkOrange,
    cakeYellow = ChirpCakeDarkYellow,
    cakeTeal = ChirpCakeDarkTeal,
    cakePurple = ChirpCakeDarkPurple,
    cakeRed = ChirpCakeDarkRed,
    cakeMint = ChirpCakeDarkMint,
)