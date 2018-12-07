package com.gerps.magazine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Grzesiek on 2018-11-17
 */

@Entity
@NoArgsConstructor
@Data
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String nip;

    private String city;
    private String street;
    private String street_number;
    private String post_code;
    private String phone_number;

    @Email
    private String email;
    private String www;

    private String representative_person;
    private String bank_supplier_name;
    private String bank_supplier_account_number;
}
