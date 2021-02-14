package pl.faustyna.gallery.translation.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class SecurityTokenRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityTokenRepository.class);
    private final ConcurrentMap<UUID, SecurityToken> map;

    public SecurityTokenRepository() {
        this.map = new ConcurrentHashMap<>();
    }


    public SecurityToken getNewSecurityToken(final String ssoToken) {
        final SecurityToken securityToken = new SecurityToken(UUID.randomUUID(), ssoToken, LocalDateTime.now());
        map.put(securityToken.getToken(), securityToken);

        return securityToken;
    }

    public Optional<SecurityToken> getSecurityTokenByToken(final String token) {
        map.forEach((u, s) -> LOG.debug(u.toString()));
        return Optional.ofNullable(map.get(UUID.fromString(token)));
    }
}
