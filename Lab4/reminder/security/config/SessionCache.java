package org.example.reminder.security.config;

import org.example.reminder.security.config.prop.CacheProperties;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Component
@EnableConfigurationProperties(CacheProperties.class)
public class SessionCache {
    private final Cache<String, Collection<GrantedAuthority>> keyCloakSessions;

    public SessionCache(CacheProperties cacheProperties){
        this.keyCloakSessions = Caffeine.newBuilder()
                .initialCapacity(cacheProperties.initialCapacity())
                .expireAfterWrite(cacheProperties.expireAfterWriteHours(), TimeUnit.HOURS)
                .maximumSize(cacheProperties.maximumSize())
                .build();
    }

    public Collection<GrantedAuthority> getAuthorities(String sessionId) {
        return keyCloakSessions.getIfPresent(sessionId);
    }

    public void putAuthorities(String sessionId,  Collection<GrantedAuthority> authorities) {
        keyCloakSessions.put(sessionId, authorities);
    }

    public boolean containsSession(String sessionId) {
        return keyCloakSessions.getIfPresent(sessionId) != null;
    }
}
