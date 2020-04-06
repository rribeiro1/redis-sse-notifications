package com.labtwin.notifications

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class NotificationMessageListener(
        private val notificationService: NotificationService
) : MessageListener {
    private val log = LoggerFactory.getLogger(NotificationMessageListener::class.java)!!
    private val objectMapper = ObjectMapper()
    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            log.debug("Message Received: ${message.body}")
            val notificationDto = objectMapper.readValue(message.body, NotificationDto::class.java)
            println(notificationDto)
            notificationService.sendEvent(notificationDto)
        } catch (e: Exception) {
            log.error("Message receive failed", e)
        }
    }
}