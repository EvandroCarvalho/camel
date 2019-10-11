package com.apachecamel.rest;

import com.apachecamel.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HttpRequestRoute extends RouteBuilder {

    @Autowired
    private ObjectMapper mapper;
    private GsonDataFormat toUserClass = new GsonDataFormat(User.class);
    @Override
    public void configure() throws Exception {
        from("direct:request")
                .log(LoggingLevel.INFO, "call http request")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET.toString()))
                .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
                .to("http4://localhost:3000/mock?bridgeEndpoint=true")
                .convertBodyTo(String.class)
                .unmarshal(toUserClass)
                .process(exchange -> {
                    User body = exchange.getIn().getBody(User.class);
                    log.info(body.getName());
                    exchange.getOut().setBody(mapper.writeValueAsString(body));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.ACCEPTED.toString());
                })
                .end();
    }
}
