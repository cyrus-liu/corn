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

import java.util.Collections;


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
                .protocols(Collections.singleton("https"))
                .host("https://www.abinya.top")
                .apiInfo(apiInfo())
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
                .title("苞米豆api文档")
                .description("一个基于VGG神经网络的花卉识别定位信息采集系统，代号：苞米豆。通过这个系统能够识别花卉的种类然后将信息保存到数据库中，并在地图上进行标记，共享给其他人查看。")
                .termsOfServiceUrl("https://www.abinya.top")
                .contact(new Contact("大只",
                        "https://www.abinya.top",
                        "1919302385@qq.com"))
                .version("1.0")
                .build();
    }

    //http://127.0.0.1:8881/doc.html#/home
    //https://www.abinya.top/doc.html#/home
}


