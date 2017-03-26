package com.sample.configuration;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Created by nlre on 24/03/2017.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    /**
     * Cards api.
     *
     * @return the docket
     */
    @Bean
    public Docket accountsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("accounts")
                .apiInfo(apiInfo())
                .select()
                .paths(accountsPaths())
                .build();
    }

    /**
     * Cards paths.
     *
     * @return the predicate
     */
    private Predicate<String> accountsPaths() {
        return PathSelectors.regex("/accounts.*");
    }

    /**
     * Api info.
     *
     * @return the api info
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Accounts API")
                .license("Apache License Version 2.0")
                .version("1.0-SNAPSHOT")
                .build();

    }




}
