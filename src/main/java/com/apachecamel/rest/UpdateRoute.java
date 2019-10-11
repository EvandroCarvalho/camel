package com.apachecamel.rest;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.apachecamel.model.User;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UpdateRoute extends RouteBuilder {

  private static final String CQL = "INSERT INTO USER (id, age, burn_date, name) values (?, ?, ?, ?)";
  
  GsonDataFormat format = new GsonDataFormat(User.class);
  
  @Autowired
  private ObjectMapper mapper = new ObjectMapper();
  
  @Override
  public void configure() throws Exception {

      
     from("direct:atualiza")
      .log(LoggingLevel.INFO, "${body}")
      .convertBodyTo(String.class)
   //   .unmarshal(format)
      .process(new Processor() {
        
        @Override
        public void process(Exchange exchange) throws Exception {
     //     String user =  exchange.getIn().getBody().toString();
       //   User userValue = mapper.readValue(user, User.class);
       //   log.info("processor" + user);
//          User userModel = null;
//          try {
//          userModel = mapper.readValue(json, User.class);
//          } catch(Exception e) {
//            log.info("ERRO NA CONVERSAO", e);
//          }
//          User userModel = User.builder().name("Carvarlho").age(30).build();
//          log.info("burn date: " + userValue.getBurn_date());
//          LocalDate localDate = userValue.getBurn_date();
//          log.info("Localdate: " + localDate);
          LocalDate localdDate = LocalDate.fromYearMonthDay(2019, 11, 11);
          
          List<Object> lista =Arrays.asList(4, 20, localdDate, "Evandro");
          log.info(lista.toString());
          exchange.getIn().setBody(lista);
        }
      })
   //   .to("{{urlCassandra}}:{{port}}/forum?cql=" + CQL)
      .process(new Processor() {
        
        @Override
        public void process(Exchange exchange) throws Exception {
          log.info("Atualizado dig dim");
          exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, "201");
         exchange.getOut().setBody("{\"Mensagem\":\"Sucesso\"\n\t\"StatusCode\":\"200\"}");
        }
      })
      .end();
  }

}
