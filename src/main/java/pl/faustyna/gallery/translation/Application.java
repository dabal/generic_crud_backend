package pl.faustyna.gallery.translation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        final HttpComponentsClientHttpRequestFactory restTemplateFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplateFactory.setConnectionRequestTimeout(20000);
        restTemplateFactory.setConnectTimeout(20000);
        restTemplateFactory.setReadTimeout(20000);
        return new RestTemplate(restTemplateFactory);
    }

}
