package wild.west.bounty.hunter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Value("{cors.originPatterns:default}")
        private String corsOriginPatterns = "";

        @Override
        public void addCorsMappings(CorsRegistry registry){
            var allowedOrigins = corsOriginPatterns.split(",");
            registry.addMapping("/**")
                    .allowedMethods("*")
                    .allowedOrigins(allowedOrigins)
                    .allowCredentials(true);
        }

        @Override
        public void extendMessageConverters(List<HttpMessageConverter<?>> converters){
//            converters.add()
        }
}
