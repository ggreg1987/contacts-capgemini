//package one.digitalinnovation.contact.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("ione.digitalinnovation.contact" +
//                        ".domain.rest.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Contacts Api")
//                .description("DIO bootcamp where need create a contacts for persons")
//                .version("1.0")
//                .contact(contact())
//                .build();
//    }
//
//    private Contact contact() {
//        return new Contact(
//                "Gabriel Gregorio",
//                "https://github.com/ggreg1987",
//                "gr3g1987@gmail.com"
//        );
//    }
//}
