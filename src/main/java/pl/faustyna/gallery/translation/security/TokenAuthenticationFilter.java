package pl.faustyna.gallery.translation.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStart;


public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String BEARER = "Bearer";
    private static final Logger LOG = LoggerFactory.getLogger(SecurityService.class);

//    public TokenAuthenticationFilter() {
//        super(new AntPathRequestMatcher(null));
//    }

    public TokenAuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response) {
        final String param = ofNullable(request.getHeader(AUTHORIZATION))
                .orElse(request.getParameter("t"));

        final String token = ofNullable(param)
                .map(value -> removeStart(value, BEARER))
                .map(String::trim)
                .orElseThrow(() -> {
                    LOG.info("BadCredentialsException - " + request.getContextPath() + " / " + request.getServletPath());
                    throw new BadCredentialsException("Missing Authentication Token");
                });
        final Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
        if (auth == null) {
            throw new BadCredentialsException("Authentication Token is invalid");
        }
        Authentication authentication = null;
        try {
            authentication = getAuthenticationManager().authenticate(auth);
        } catch (final BadCredentialsException e) {
            LOG.error(new StringBuilder().append(token).append(" - ").append(request.getContextPath()).append("/").append(request.getServletPath()).toString());
            throw e;
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}