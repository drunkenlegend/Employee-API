package com.api.employee.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Employee API",
            description = "This api provides CRUD operations on Employee Entity",
            version = "1.0",
            contact = @Contact(name = "Saksham Gupta", email = "saksham1319@gmail.com")),
    security =
        @SecurityRequirement(
            name = "bearerAuth",
            scopes = {}))
@SecurityScheme(type = SecuritySchemeType.HTTP,name = "bearerAuth",scheme = "bearer")
public class OpenAPIConfiguration {}
