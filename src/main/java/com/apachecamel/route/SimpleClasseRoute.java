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

        from("timer:hello?period=10s")
                .log("Timer invoked and the body is ${body}")
                .pollEnrich("file:data/input?delete=true&readLock=none")
                .to("file:data/output");
    }
}