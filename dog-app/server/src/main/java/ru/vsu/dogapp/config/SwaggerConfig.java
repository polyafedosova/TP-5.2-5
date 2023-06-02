package ru.vsu.dogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.vsu.dogapp.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false) // Disable default response messages
                .globalResponses(HttpMethod.GET, getDefaultResponses()) // Add default responses for GET requests
                .globalResponses(HttpMethod.POST, getDefaultResponses()) // Add default responses for POST requests
                .globalResponses(HttpMethod.PUT, getDefaultResponses()) // Add default responses for PUT requests
                .globalResponses(HttpMethod.DELETE, getDefaultResponses()); // Add default responses for DELETE requests
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("\"Paws\". The server part.")
                .description("The server part of the application for dog breeders \"Paws\".")
                .version("1.0.0")
                .contact(new Contact("TP-5.2-5", "https://github.com/polyafedosova/TP-5.2-5", "artem.polev.02@mail.ru"))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(context -> true)
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("Bearer", authorizationScopes));
    }

    private List<Response> getDefaultResponses() {
        Response error200 = new ResponseBuilder()
                .code("200")
                .description("OK")
                .build();

        Response error201 = new ResponseBuilder()
                .code("201")
                .description("Created")
                .build();

        Response error401 = new ResponseBuilder()
                .code("401")
                .description("Unauthorized")
                .build();

        Response error403 = new ResponseBuilder()
                .code("403")
                .description("Access Denied")
                .build();

        Response error404 = new ResponseBuilder()
                .code("404")
                .description("Not Found")
                .build();

        Response error500 = new ResponseBuilder()
                .code("500")
                .description("Internal Server Error")
                .build();

        return Arrays.asList(error200, error201, error401, error403, error404, error500);
    }
}