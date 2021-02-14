package pl.faustyna.gallery.translation.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EndpointRequestMatcherProvider {
    private final RequestMatcher requestMatchers;

    public EndpointRequestMatcherProvider() {
        final List<RequestMatcher> requestMatchers = new ArrayList<>();
        requestMatchers.add(new AntPathRequestMatcher("/login/**"));
        requestMatchers.add(new AntPathRequestMatcher("/error"));
        requestMatchers.add(new AntPathRequestMatcher("/meta/reload/**"));
        this.requestMatchers = new OrRequestMatcher(requestMatchers);

    }

    public RequestMatcher getPublicURL() {
        return this.requestMatchers;
    }

    public RequestMatcher getPrivateURL() {
        return new NegatedRequestMatcher(this.requestMatchers);
    }


}
