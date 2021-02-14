package pl.faustyna.gallery.translation.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class ControllerLoggingInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerLoggingInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response, final Object handler) {

        final HttpServletRequest requestCacheWrapperObject
                = new ContentCachingRequestWrapper(request);

        try {
            LOG.debug(new StringBuilder().append(requestCacheWrapperObject.getRequestURL()).append(" ")
                    .append(requestCacheWrapperObject.getMethod()).append(" ")
                    .append(requestCacheWrapperObject.getReader().lines().collect(Collectors.joining())).toString());
        } catch (final IOException e) {
            LOG.debug(new StringBuilder().append(requestCacheWrapperObject.getRequestURL()).append(" ")
                    .append(requestCacheWrapperObject.getMethod()).toString());
        }
        return true;
    }

    @Override
    public void afterCompletion(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler,
            final Exception ex) {
        try {
            final ResponseWrapper responseWrapper = (ResponseWrapper) response;
            LOG.debug(response.toString());
        } catch (final ClassCastException e) {
            response.getStatus();
        }

    }
}
