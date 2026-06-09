package org.example.reminder.security.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cache")
public record CacheProperties(int initialCapacity,
                              int expireAfterWriteHours,
                              int maximumSize) {
}
