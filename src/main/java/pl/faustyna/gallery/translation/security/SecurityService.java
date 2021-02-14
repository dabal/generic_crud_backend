package pl.faustyna.gallery.translation.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@PropertySource("classpath:application.properties")
@Service
public class SecurityService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityService.class);
    private final SSOTokenRepository ssoTokenRepository;
    private final SecurityTokenRepository securityTokenRepository;
    @Value("${faustyna.token_should_be_checked:true}")
    private boolean shouldTokenBeChecked;

    @Value("${faustyna.sso_token_should_be_invalidated:true}")
    private boolean ssoTokenShouldBeInvalidated;

    @Autowired
    public SecurityService(final SSOTokenRepository ssoTokenRepository, final SecurityTokenRepository securityTokenRepository) {
        this.ssoTokenRepository = ssoTokenRepository;
        this.securityTokenRepository = securityTokenRepository;
    }

    public SecurityToken getSecurityTokenForSSOToken(final String ssoToken) {
        if (shouldTokenBeChecked) {
            if (ssoTokenRepository.isSSOTokenValid(ssoToken)) {
                if (ssoTokenShouldBeInvalidated) {
                    ssoTokenRepository.invalidateSSOToken(ssoToken);
                }

            } else {
                LOG.error(String.format("invalid token: %s", ssoToken));
                throw new ResourceNotFoundException(String.format("invalid token: %s", ssoToken));
            }
        }
        return securityTokenRepository.getNewSecurityToken(ssoToken);
    }

    public User getUserForSecurityToken(final String token) {
        final User user = new User();
        user.setSecurityToken(securityTokenRepository.getSecurityTokenByToken(token)
                .orElseThrow(() -> {
                    throw new BadCredentialsException("Bad Authentication Token");
                }));
        return user;
    }


}



