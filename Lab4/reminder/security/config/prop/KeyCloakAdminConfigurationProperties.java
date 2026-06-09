package org.example.reminder.security.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeyCloakAdminConfigurationProperties(String authServerUrl,
                                                   String realm,
                                                   String resource,
                                                   String redirectUrl,
                                                   Credentials credentials) {
    public record Credentials(String secret) {}

}
