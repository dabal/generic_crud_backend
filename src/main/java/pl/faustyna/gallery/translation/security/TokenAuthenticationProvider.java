package pl.faustyna.gallery.translation.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component

public final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(TokenAuthenticationProvider.class);
    private final SecurityService auth;


    @Autowired
    public TokenAuthenticationProvider(@NonNull final SecurityService auth) {
        this.auth = auth;
    }

    @Override
    protected void additionalAuthenticationChecks(final UserDetails d, final UsernamePasswordAuthenticationToken auth) {
        // Nothing to do
    }

    @Override
    protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) {
        final String token = (String) authentication.getCredentials();
        LOG.debug("token - " + token);
        return auth.getUserForSecurityToken(token);
    }
}