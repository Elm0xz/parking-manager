package com.pretz.parkingmanager.swagger;

import com.google.common.base.Predicate;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.and;
import static springfox.documentation.builders.PathSelectors.regex;

@Profile({"dev", "test"})
@Setter
@EnableSwagger2
@Configuration
@ConfigurationProperties(prefix = "parking-manager")
public class SwaggerConfig {

    private static final String NO_ERROR_REGEX = "(?!.*error).*$";
    private static final String NO_SWAGGER_REGEX = "(?!.*swagger).*$";

    private String title;
    private String description;
    private String version;
    private String contactName;
    private String contactUrl;
    private String contactAddress;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .contact(new Contact(contactName, contactUrl, contactAddress))
                .build();

    }

    private Predicate<String> paths() {
        return and(regex(NO_ERROR_REGEX), regex(NO_SWAGGER_REGEX));
    }
}