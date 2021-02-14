package pl.faustyna.gallery.translation.logging;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestResponseWrapperFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        // DO NOTHING
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        // Wrap the request and response
        final ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
        final RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
        chain.doFilter(requestWrapper, responseWrapper);
    }

    @Override
    public void destroy() {
        // DO NOTHING
    }
}