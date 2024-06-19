package com.toshitdev.stocksapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.toshitdev.stocksapp.data.remote.dto.IntradayDto
import com.toshitdev.stocksapp.domain.model.Intraday
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayDto.toIntraday(): Intraday {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp,formatter)
    return Intraday(
        date = localDateTime,
        close = close
    )
}