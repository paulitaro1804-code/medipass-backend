package com.medipass.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI medipassOpenAPI() {
        final String securitySchemeName = "BearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("MEDIPASS API")
                        .description("Sistema de Gestión de Atención Médica Prioritaria para Asegurados Privados en Colombia.\n\n" +
                                "## Autenticación\nUsa el botón **Authorize** e ingresa: `Bearer <tu_token_JWT>`\n\n" +
                                "## Usuarios demo\n" +
                                "| Email | Contraseña | Rol |\n" +
                                "|-------|-----------|-----|\n" +
                                "| admin@medipass.com | admin123 | ADMIN |\n" +
                                "| doctor@medipass.com | doctor123 | DOCTOR |\n" +
                                "| coordinador@medipass.com | coord123 | COORDINATOR |\n" +
                                "| validador@medipass.com | valid123 | VALIDATOR |\n" +
                                "| lab@medipass.com | lab123 | LAB |\n" +
                                "| soporte@medipass.com | soporte123 | SUPPORT |\n" +
                                "| carlos.ramirez@gmail.com | paciente123 | PATIENT |")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MEDIPASS Team")
                                .email("andres.duarte@medipass.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Ingresa el token JWT sin el prefijo 'Bearer '")));
    }
}
