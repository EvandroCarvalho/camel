package com.apachecamel.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataDeserialize extends JsonDeserializer<LocalDate> {

  @Override
  public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
//    log.info("DATE: " + p.getText());
//    String[] date = p.getText().split("-");
    log.info("deserialize: " + p.getText());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = null;
    localDate = LocalDate.parse(p.getText(), formatter);
    
//    log.info("DATE ARRAY: " + date[0]);
//    
//    LocalDate localdate = LocalDate.fromYearMonthDay(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
//    log.info("LOCALDATE: " + localdate);
    return localDate;
  }
  
//  
//  public static void main(String[] args) {
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    LocalDate localDate = LocalDate.parse("2019-12-12", formatter);
//    
//    System.out.println(localDate);
//  }

}
