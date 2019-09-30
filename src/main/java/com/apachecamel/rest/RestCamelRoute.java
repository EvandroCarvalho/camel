package com.apachecamel.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import com.apachecamel.model.Response;
import com.apachecamel.model.User;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Evandro Carvalho
 */
@Component
@Slf4j
public class RestCamelRoute extends RouteBuilder {

  @Autowired
  private ObjectMapper mapper;

  private static String CQL = "select json * from user where burn_date > ? ALLOW FILTERING";
  // private static String CQL = "select * from user where name = ? ALLOW FILTERING";

  @Override
  public void configure() throws Exception {

    rest("/hello").get().to("direct:hello");

    from("direct:hello").doTry().log("bigodin finin").process(new Processor() {
      @Override
      public void process(Exchange exchange) throws Exception {
        log.info("in processor");
        LocalDate date = LocalDate.fromYearMonthDay(1985, 12, 19);
        exchange.getIn().setBody(date);
      }
    }).log(LoggingLevel.INFO, "chamando o banco").log(LoggingLevel.INFO, "${body}")
        .to("cql://localhost/forum?cql=" + CQL).log("consulta realizada").log("${body}")
        .process(new Processor() {
          @Override
          public void process(Exchange exchange) throws Exception {
            // Row

            // List<Row> result = (List<Row>) exchange.getIn().getBody();
            //
            // log.info("List Of Row" + result.toString());
            //
            // int id = Integer.parseInt(result.get(0).getObject("id").toString());
            // String name = result.get(0).getObject("name").toString();
            // int age = Integer.parseInt(result.get(0).getObject("age").toString());
            //
            // User user = User.builder().id(id).age(age).name(name).build();
            //
            // Object obj = exchange.getIn().getBody();
            // log.info("Response consult: " + obj.toString());
            // exchange.getOut().setBody(new
            // ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(user));
            // exchange.getOut().setHeader("Content-type",MediaType.APPLICATION_JSON_VALUE);

            // Json
            @SuppressWarnings("unchecked")
            List<Row> rows = (List<Row>) exchange.getIn().getBody();
            String jsonResponse = rows.get(0).getObject(0).toString();
            log.info("JSON: " + jsonResponse);

            List<User> listResponse = new ArrayList<>();
            Response response = new Response();
            
            for (Row row : rows) {
              User user = mapper.readValue(row.getObject(0).toString(), User.class);
              listResponse.add(user);
            }

            response.setData(listResponse);

            exchange.getOut().setBody(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
            exchange.getOut().setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE);
          }
        }).endDoTry().doCatch(IOException.class).log("Erro na consulta").end();

  }
}
