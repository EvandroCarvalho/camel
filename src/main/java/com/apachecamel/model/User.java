package com.apachecamel.model;

import java.time.LocalDate;
import com.apachecamel.util.DataDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Evandro Carvalho
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
  
  @EqualsAndHashCode.Include
  private int id;
  private String name;
  private int age;
  @JsonDeserialize(using = DataDeserialize.class)
  private LocalDate burn_date;
}
