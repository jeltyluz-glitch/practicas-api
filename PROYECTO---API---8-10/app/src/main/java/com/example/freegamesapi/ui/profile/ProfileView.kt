package com.example.freegamesapi.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freegamesapi.model.Usuario
import java.util.Locale

@Composable
fun ProfileView(
    usuario: Usuario = Usuario(1048, "Alex Lara", "alex.lara@email.com")
) {
    val backgroundColor = Color(0xFF0F0F13)
    val cardColor = Color(0xFF181820)
    val primaryPurple = Color(0xFFC4B5FD)
    val textColor = Color.White
    val mutedTextColor = Color(0xFF9CA3AF)
    val borderDark = Color(0xFF2A2A38)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Información de tu cuenta y preferencias.",
            fontSize = 13.sp,
            color = mutedTextColor,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Main Profile Card (Top Section)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = cardColor,
            border = BorderStroke(1.dp, borderDark)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar Initials Circle
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(primaryPurple),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = getInitials(usuario.nombre),
                        color = Color(0xFF0F0F13),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Name
                Text(
                    text = usuario.nombre,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Email
                Text(
                    text = usuario.email,
                    fontSize = 14.sp,
                    color = mutedTextColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Active Status Pill
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF1F2937)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFA7F3D0))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Cuenta activa",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFA7F3D0)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(color = borderDark, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))

                // Stats Row (Favorites & Explored)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${usuario.verFavoritos().size.coerceAtLeast(2)}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Favoritos",
                            fontSize = 12.sp,
                            color = mutedTextColor
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .width(1.dp)
                            .background(borderDark)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "6",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Explorados",
                            fontSize = 12.sp,
                            color = mutedTextColor
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Personal Information Section
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Información personal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            InfoFieldCard(
                label = "Nombre",
                value = usuario.nombre,
                cardColor = cardColor,
                borderDark = borderDark,
                mutedTextColor = mutedTextColor,
                textColor = textColor
            )

            InfoFieldCard(
                label = "Correo electrónico",
                value = usuario.email,
                cardColor = cardColor,
                borderDark = borderDark,
                mutedTextColor = mutedTextColor,
                textColor = textColor
            )

            InfoFieldCard(
                label = "ID de usuario",
                value = "#FGF-${String.format(Locale.getDefault(), "%04d", usuario.id)}",
                cardColor = cardColor,
                borderDark = borderDark,
                mutedTextColor = mutedTextColor,
                textColor = primaryPurple
            )

            InfoFieldCard(
                label = "Miembro desde",
                value = "Octubre de 2024",
                cardColor = cardColor,
                borderDark = borderDark,
                mutedTextColor = mutedTextColor,
                textColor = textColor
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Preferences Section
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Preferencias",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Preference Card 1: Plataforma principal
            PreferenceItemCard(
                label = "Plataforma principal",
                value = "PC (Windows)",
                cardColor = cardColor,
                borderDark = borderDark,
                mutedTextColor = mutedTextColor,
                textColor = textColor
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF2E2A47)
                ) {
                    Text(
                        text = "PC",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = primaryPurple,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Preference Card 2: Géneros favoritos
            PreferenceItemCard(
                label = "Géneros favoritos",
                value = "Shooter y MMORPG",
                cardColor = cardColor,
                borderDark = borderDark,
                mutedTextColor = mutedTextColor,
                textColor = textColor
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF22222D),
                        border = BorderStroke(1.dp, borderDark)
                    ) {
                        Text(
                            text = "Shooter",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            color = mutedTextColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF22222D),
                        border = BorderStroke(1.dp, borderDark)
                    ) {
                        Text(
                            text = "MMORPG",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            color = mutedTextColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoFieldCard(
    label: String,
    value: String,
    cardColor: Color,
    borderDark: Color,
    mutedTextColor: Color,
    textColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = cardColor,
        border = BorderStroke(1.dp, borderDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = mutedTextColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
        }
    }
}

@Composable
fun PreferenceItemCard(
    label: String,
    value: String,
    cardColor: Color,
    borderDark: Color,
    mutedTextColor: Color,
    textColor: Color,
    trailingContent: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = cardColor,
        border = BorderStroke(1.dp, borderDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = mutedTextColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
            }
            trailingContent()
        }
    }
}

fun getInitials(name: String): String {
    val parts = name.trim().split("\\s+".toRegex())
    return if (parts.size >= 2) {
        "${parts[0].take(1)}${parts[1].take(1)}".uppercase()
    } else if (parts.isNotEmpty() && parts[0].isNotEmpty()) {
        parts[0].take(2).uppercase()
    } else {
        "AL"
    }
}
