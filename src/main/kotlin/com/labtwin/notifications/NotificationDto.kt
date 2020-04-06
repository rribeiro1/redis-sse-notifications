package com.labtwin.notifications

import com.fasterxml.jackson.annotation.JsonProperty

data class NotificationDto(
        @JsonProperty("userId")
        val userId: String,
        @JsonProperty("message")
        val message: String
)
