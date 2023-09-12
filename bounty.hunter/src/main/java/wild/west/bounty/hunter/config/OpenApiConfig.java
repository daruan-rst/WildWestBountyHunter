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
                       .title("API proof of concept de melhorias etc etc")
                       .version("COLOQUE AQUI O NÚMERO DA SUA VERSÃO")
                       .description("Descrição qualquer sobre essa API")
                       .termsOfService("www.termosdeserviço.com")
                       .license(
                               new License()
                                .name("Apache 2.0")
                                .url("www.sitedelicença.com")));
    }


}
