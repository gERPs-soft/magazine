package com.gerps.magazine.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Grzesiek on 2018-11-17
 */

@Entity
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String city;
    private String street;
    private String street_number;
    private String post_code;
    private String phone_number;
    private String email;
    private String www;

    private String representativePerson;
    private String bankSupplierName;
    private Long bankSupplierAccountNumber;


}
