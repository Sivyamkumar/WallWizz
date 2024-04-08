package com.example.wallpaper.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.wallpaper.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Signika Negative")
val fontName2 = GoogleFont("Amaranth")
val fontName3 = GoogleFont("Playfair Display")
val fontName4 = GoogleFont("Acme")
val fontName5 = GoogleFont("Chivo")

val Signikafont= FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = provider
    )
)

val Amaranth = FontFamily(
    Font(
        googleFont = fontName2,
        fontProvider = provider
    )
)

val playfair = FontFamily(
    Font(
        googleFont = fontName3,
        fontProvider = provider
    )
)

val Acme = FontFamily(
    Font(
        googleFont = fontName4,
        fontProvider = provider
    )
)

val Chivo = FontFamily(
    Font(
        googleFont = fontName5,
        fontProvider = provider
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)