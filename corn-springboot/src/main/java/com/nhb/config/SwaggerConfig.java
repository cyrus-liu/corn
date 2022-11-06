package com.nhb.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger配置类
 *
 * @author 大只
 * @date 2022-11-06
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean(value = "defaultApi")
    public Docket defaultApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("2.X版本")
                .select()
                //这里指定Controller扫描包路径
                //.apis(RequestHandlerSelectors.basePackage("com.nhb.controller"))
                //这里指定扫描有ApiOperation注解的类
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //所有路径
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("大只的APIs")
                .description("dorm项目接口")
                .termsOfServiceUrl("https://blog.csdn.net/weixin_43453386")
                .contact(new Contact("大只",
                        "https://blog.csdn.net/weixin_43453386",
                        "xxx@163.com"))
                .version("1.0")
                .build();
    }

    //http://127.0.0.1:8881/doc.html#/home
}


