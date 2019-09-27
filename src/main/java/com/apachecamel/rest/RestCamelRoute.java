package com.apachecamel.rest;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * @author Evandro Carvalho
 */
@Component
public class RestCamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        String cql = "select * from user;";

        rest("/hello")
                .get()
                .to("direct:hello");

        from("direct:hello")
                .log("bigodin finin")
              //  .setHeader(Exchange.HTTP_METHOD, simple(HttpMethod.GET.name()))
                .to("cql://localhost:9042?keyspace=forum&cql=" + cql)
                .log("${body}");


    }
}
