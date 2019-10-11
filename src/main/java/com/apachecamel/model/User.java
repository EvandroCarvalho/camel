package com.apachecamel.model;

import java.time.LocalDate;
import com.apachecamel.util.DataDeserialize;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

/**
 * @author Evandro Carvalho
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class User {
  
//  private int id;
  private String name;
//  private int age;
//  @JsonDeserialize(using = DataDeserialize.class)
//  private LocalDate burn_date;
}
