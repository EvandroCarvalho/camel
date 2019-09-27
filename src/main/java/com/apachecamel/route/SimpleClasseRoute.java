package com.apachecamel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Evandro Carvalho
 */
@Component
public class SimpleClasseRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("{{fromRoute}}")
                .log("body is ${body}")
                .to("{{toRoute}}");
    }
}