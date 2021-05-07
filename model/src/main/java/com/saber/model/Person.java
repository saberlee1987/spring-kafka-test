package com.saber.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
@Data
@EqualsAndHashCode
@ToString
public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private Integer age;
    private String mobileNumber;
}
