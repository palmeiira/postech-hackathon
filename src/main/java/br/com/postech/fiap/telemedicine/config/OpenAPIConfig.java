package br.com.postech.fiap.telemedicine.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class OpenAPIConfig implements WebMvcConfigurer {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("br.com.postech.fiap.telemedicine") // Substitua pelo pacote base da sua aplicação
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Nome do Serviço")
                        .version("1.0.0")
                        .description("Descrição da API")
                        .contact(new Contact()
                                .name("Dev-Team")
                                .url("https://www.dev-team.com/")
                                .email("dev-team@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}