package wild.west.bounty.hunter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI(){
       return new OpenAPI()
               .info(new Info()
                       .title("API proof of concept")
                       .version("1")
                       .description("This wild west themed api was designed for me to practice some concepts on java development")
                       .termsOfService("www.termosdeservico.com")
                       .license(
                               new License()
                                .name("Apache 2.0")
                                .url("www.sitedelicença.com")));
    }


}
