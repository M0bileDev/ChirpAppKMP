package com.example.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import chirpappkmp.core.designsystem.generated.resources.Res
import chirpappkmp.core.designsystem.generated.resources.plusjakartasans_bold
import chirpappkmp.core.designsystem.generated.resources.plusjakartasans_light
import chirpappkmp.core.designsystem.generated.resources.plusjakartasans_medium
import chirpappkmp.core.designsystem.generated.resources.plusjakartasans_regular
import chirpappkmp.core.designsystem.generated.resources.plusjakartasans_semibold
import org.jetbrains.compose.resources.Font

val PlusJakartaSans
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.plusjakartasans_light,
            weight = FontWeight.Light
        ),
        Font(
            resource = Res.font.plusjakartasans_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.plusjakartasans_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resource = Res.font.plusjakartasans_semibold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resource = Res.font.plusjakartasans_bold,
            weight = FontWeight.Bold
        )
    )