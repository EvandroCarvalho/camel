package com.apachecamel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Evandro Carvalho
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private int id;
    private String name;
    private int age;
}
