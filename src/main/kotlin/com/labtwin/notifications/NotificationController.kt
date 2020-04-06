package com.labtwin.notifications

import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@PreAuthorize("hasAuthority('USER')")
@RestController
class NotificationController(
        val notificationService: NotificationService
) {
    val log = LoggerFactory.getLogger(NotificationController::class.java)!!

    @GetMapping("/notifications")
    fun subscribeNotifications(
            @AuthenticationPrincipal userDetails: UserDetails
    ): SseEmitter {
        val sseEmitter = SseEmitter(Long.MAX_VALUE)

        notificationService.subscribe(userDetails.username, sseEmitter)

        log.info("${userDetails.username} subsribd.")

        sseEmitter.onCompletion {
            notificationService.unsubscribe(sseEmitter)
            log.info("${userDetails.username} unsubsribd.")
        }

        sseEmitter.onError {
            notificationService.unsubscribe(sseEmitter)
            log.info("${userDetails.username} unsubsribd.")
        }

        return sseEmitter
    }
}