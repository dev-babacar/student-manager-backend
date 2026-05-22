package com.babacarmane.studentmanagerbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI studentManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                                .title("StudentManager API")
                                // ↑ Titre affiché en haut de Swagger

                                .description("API REST de gestion des étudiants, cours et notes — " +
                                        "Projet Spring Boot par BabacarDev")
                                // ↑ Description générale

                                .version("v1.0.0")
                                // ↑ Version de ton API

                                .contact(new Contact()
                                        .name("BabacarDev")
                                        .email("babacar.mane.dev@gmail.com"))
                        // ↑ Tes infos de contact
                )
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Serveur de développement"));
    }
}
