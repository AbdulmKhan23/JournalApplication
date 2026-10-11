package com.khan.journalapplication.presentation.components


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khan.journalapplication.R

@Composable
fun ReflectButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val surfaceColor = colorResource(R.color.app_surface)
    val textColor = colorResource(R.color.app_text)
    val accentColor = colorResource(R.color.app_accent)

    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(76.dp),
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 3.dp,
            brush = SolidColor(textColor)
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = surfaceColor,
            contentColor = accentColor
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .border(
                        width = 2.dp,
                        color = accentColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = null,
                    modifier = Modifier.size(27.dp),
                    tint = accentColor
                )
            }

            Text(
                text = "Reflect",
                color = accentColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
