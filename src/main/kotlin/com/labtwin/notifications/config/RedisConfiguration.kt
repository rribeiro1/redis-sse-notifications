package com.labtwin.notifications.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
@ConfigurationProperties(prefix = "redis")
class RedisConfiguration {
    lateinit var host: String

    @Bean
    fun connectionFactory(): JedisConnectionFactory {
        val config = RedisStandaloneConfiguration(host)
        return JedisConnectionFactory(config)
    }

    @Bean
    fun redisTemplateBean(connectionFactory: JedisConnectionFactory) : RedisTemplate<Any, Any> {
        val redisTemplate = RedisTemplate<Any, Any>()
        redisTemplate.setConnectionFactory(connectionFactory)
        return redisTemplate
    }
}
