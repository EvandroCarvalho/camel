package com.apachecamel.rest;

import com.apachecamel.model.User;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author Evandro Carvalho
 */
@Component
@Slf4j
public class RestCamelRoute extends RouteBuilder {
    private static String CQL = "select * from user where id = ?";
    //private static String CQL = "select * from user where name = ? ALLOW FILTERING";

    @Override
    public void configure() throws Exception {

        rest("/hello")
                .get()
                .to("direct:hello");

        from("direct:hello")
                .doTry()
                    .log("bigodin finin")
                    .process(new Processor() {
                        @Override
                        public void process(Exchange exchange) throws Exception {
                            log.info("in processor");
                            exchange.getIn().setBody(1);
                        }
                    })
                    .log(LoggingLevel.INFO, "chamando o banco")
                    .log(LoggingLevel.INFO, "${body}")
                    .to("cql://localhost/forum?cql=" + CQL)
                    .log("consulta realizada")
                    .log("${body}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        List<Row> result = (List<Row>) exchange.getIn().getBody();

                        log.info("List Of Row" + result.toString());

                        int id = Integer.parseInt(result.get(0).getObject("id").toString());
                        String name = result.get(0).getObject("name").toString();
                        int age = Integer.parseInt(result.get(0).getObject("age").toString());

                        User user = User.builder().id(id).age(age).name(name).build();

                        Object obj = exchange.getIn().getBody();
                        log.info("Response consult: " + obj.toString());
                        exchange.getOut().setBody(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(user));
                        exchange.getOut().setHeader("Content-type",MediaType.APPLICATION_JSON_VALUE);
                    }
                })
                .endDoTry()
                .doCatch(IOException.class)
                .log("Erro na consulta")
                .end();


    }
}
