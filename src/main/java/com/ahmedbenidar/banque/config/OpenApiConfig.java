package com.ahmedbenidar.banque.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI banqueOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("API Banque - Spring RestController")
                .version("1.0.0")
                .description("Gestion des comptes bancaires en JSON et XML : consultation, "
                        + "creation, mise a jour, changement de statut, suppression et statistiques.")
                .contact(new Contact().name("Ahmed Benidar").email("ahmed.benidar@example.ma"))
                .license(new License().name("Usage pedagogique - TP 8")));
    }
}
