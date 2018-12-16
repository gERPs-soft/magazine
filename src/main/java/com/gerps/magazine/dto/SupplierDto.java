package com.gerps.magazine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Grzesiek on 2018-11-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto implements Serializable {

    private Long id;
    private String name;
    private String nip;
    private String city;
    private String street;
    private String street_number;
    private String post_code;
    private String phone_number;
    private String email;
    private String www;

    private String representative_person;
    private String bank_supplier_name;
    private String bank_supplier_account_number;

}
