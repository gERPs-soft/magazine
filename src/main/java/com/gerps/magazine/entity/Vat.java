package com.gerps.magazine.entity;

import lombok.Data;
import lombok.Getter;

/**
 * Created by Grzesiek on 2018-11-17
 */

@Getter
public enum Vat {

    VAT_23(23),  //calls constructor with value 3
    VAT_8(8),  //calls constructor with value 2
    VAT_0(0)   //calls constructor with value 1
    ; // semicolon needed when fields / methods follow

    private int vatValue;

    private Vat(int vatValue) {
        this.vatValue = vatValue;
    }



}



