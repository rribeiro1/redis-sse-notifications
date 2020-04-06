package com.labtwin.notifications

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

data class Subscription(
    val userId: String,
    val sseEmitter: SseEmitter
)