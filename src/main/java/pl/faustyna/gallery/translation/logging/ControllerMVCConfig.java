package pl.faustyna.gallery.translation.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ControllerMVCConfig implements WebMvcConfigurer {

    @Autowired
    private ControllerLoggingInterceptor controllerLoggingInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(controllerLoggingInterceptor)
                .addPathPatterns("/**");
    }
}