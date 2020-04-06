package com.labtwin.notifications.config

import com.labtwin.notifications.NotificationMessageListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter

@Configuration
class MessagingConfiguration(
        private val notificationMessageListener: NotificationMessageListener
) {
    val topic = ChannelTopic("demo.notifications")
    val notificationsMessageListenerAdapter = MessageListenerAdapter(notificationMessageListener)

    @Bean
    fun messageListenerContainer(
            connectionFactory: JedisConnectionFactory
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        container.addMessageListener(notificationsMessageListenerAdapter, topic)
        return container
    }
}