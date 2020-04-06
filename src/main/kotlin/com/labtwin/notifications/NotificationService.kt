package com.labtwin.notifications

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

@Service
class NotificationService {

    val log = LoggerFactory.getLogger(NotificationService::class.java)!!

    private val subscriptions = CopyOnWriteArrayList<Subscription>()

    fun sendEvent(notificationDto: NotificationDto) {
        subscriptions.filter { it.userId == notificationDto.userId }.forEach {
            val event = SseEmitter.event()
                    .name("sync")
                    .data(notificationDto)
                    .id(notificationDto.userId).build()
            try {
                it.sseEmitter.send(event)
            } catch (e: IOException) {
                it.sseEmitter.complete()
            }
        }
    }

    fun subscribe(userId: String, sseEmitter: SseEmitter) {
        subscriptions.addIfAbsent(Subscription(userId, sseEmitter))
        log.info("subscribe subscriptions: ${subscriptions.size}")
    }

    fun unsubscribe(sseEmitter: SseEmitter) {
        subscriptions.filter { it.sseEmitter === sseEmitter }
                .forEach {
                    subscriptions.remove(it)
                }
        log.info("subscribe subscriptions: ${subscriptions.size}")
    }

    private fun healthCheck() {
        subscriptions.forEach {
            try {
                it.sseEmitter.send("ping")
            } catch (e: IOException) {
                it.sseEmitter.complete()
            }
        }
    }
}
