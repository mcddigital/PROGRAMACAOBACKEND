package br.com.devshowcase.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI devShowcaseOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("DevShowcase API")
                .description("API para cadastro de perfis, projetos, tecnologias, feedbacks e upvotes")
                .version("1.0.0"));
    }
}
