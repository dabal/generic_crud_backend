package pl.faustyna.gallery.translation.security;

import java.time.LocalDateTime;
import java.util.UUID;

public class SecurityToken {
    private final String ssoToken;
    private UUID token;
    private LocalDateTime creationDateTime;

    public SecurityToken(final UUID token, final String ssoToken, final LocalDateTime creationDateTime) {
        this.token = token;
        this.ssoToken = ssoToken;
        this.creationDateTime = creationDateTime;

    }

    public String getSsoToken() {
        return ssoToken;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(final LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(final UUID token) {
        this.token = token;
    }

}
