package com.example.lotus.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun getTimeAgo(createdOn: String): String {
    // Định dạng của thời gian nhận vào
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")

    val createdDate = dateFormat.parse(createdOn)
    val currentDate = Date()

    // Tính khoảng cách giữa thời gian hiện tại và thời gian tạo bài viết
    val durationMillis = currentDate.time - (createdDate?.time ?: 0)

    val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(durationMillis)
    val days = TimeUnit.MILLISECONDS.toDays(durationMillis)

    return when {
        minutes < 1 -> "now"
        minutes < 60 -> "$minutes phút trước"
        hours < 24 -> "$hours giờ trước"
        days < 7 -> "$days ngày trước"
        else -> {
            val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
            val isSameYear = yearFormat.format(createdDate) == yearFormat.format(currentDate)

            // Hiển thị theo dd-MM nếu cùng năm, ngược lại hiển thị dd-MM-yyyy
            if (isSameYear) {
                SimpleDateFormat("dd 'tháng' MM", Locale.getDefault()).format(createdDate)
            } else {
                SimpleDateFormat("dd 'tháng' MM, yyyy", Locale.getDefault()).format(createdDate)
            }
        }
    }
}

