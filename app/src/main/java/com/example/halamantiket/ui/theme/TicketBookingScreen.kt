package com.example.halamantiket

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import com.example.halamantiket.TicketUiState

// Stateful Composable: Mengelola State & Event
@Composable
fun TicketBookingScreen() {
    var uiState by remember { mutableStateOf(TicketUiState()) }
    val context = LocalContext.current

    val onIncrease = { uiState = uiState.copy(ticketCount = uiState.ticketCount + 1) }
    val onDecrease = {
        if (uiState.ticketCount > 1) {
            uiState = uiState.copy(ticketCount = uiState.ticketCount - 1)
        }
    }
    val onReset = { uiState = uiState.copy(ticketCount = 1) }

    val onShareOrder = {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Pemesanan Tiket Berhasil!\nJumlah: ${uiState.ticketCount} tiket\nTotal: Rp${String.format("%,d", uiState.totalPrice).replace(',', '.')}"
            )
        }
        context.startActivity(Intent.createChooser(shareIntent, "Bagikan Tiket Via"))
    }

    // Variabel & Lambda dikirim ke Stateless UI melalui parameter
    TicketBookingContent(
        uiState = uiState,
        onIncrease = onIncrease,
        onDecrease = onDecrease,
        onReset = onReset,
        onShareOrder = onShareOrder
    )
}

// Stateless Composable: Hanya menerima variabel & aksi via Parameter
@Composable
fun TicketBookingContent(
    uiState: TicketUiState,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onReset: () -> Unit,
    onShareOrder: () -> Unit
) {
    val primaryBlue = Color(0xFF1E88E5)
    val accentGreen = Color(0xFF00897B)
    val buttonRed = Color(0xFFE53935)
    val cardBg = Color(0xFFFAFAFA)
    val quantityBg = Color(0xFFF0F2F5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ConfirmationNumber,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(52.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Pemesanan Tiket",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Pesan tiket dengan mudah!",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Harga Tiket", color = Color.DarkGray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Rp${String.format("%,d", uiState.ticketPrice).replace(',', '.')}",
                            color = primaryBlue,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("per tiket", color = Color.Gray, fontSize = 12.sp)
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Jumlah Tiket", color = Color.DarkGray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = onDecrease,
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(primaryBlue, CircleShape)
                            ) {
                                Text("-", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 16.dp)
                                    .height(44.dp)
                                    .background(quantityBg, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${uiState.ticketCount}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }

                            IconButton(
                                onClick = onIncrease,
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(primaryBlue, CircleShape)
                            ) {
                                Text("+", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Total", color = Color.DarkGray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Rp${String.format("%,d", uiState.totalPrice).replace(',', '.')}",
                            color = accentGreen,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onReset,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonRed),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("RESET", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                OutlinedButton(
                    onClick = onShareOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("BAGIKAN TIKET", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}

class Icons {
    constructor(imageVector: Any, contentDescription: Nothing?, tint: Color, modifier: Modifier)

}
