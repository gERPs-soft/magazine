package com.gerps.magazine.entity;

import lombok.Data;
import lombok.Getter;

/**
 * Created by Grzesiek on 2018-11-17
 */

@Getter
public enum Vat {

/*
    int value;

    Vat(int value) {
        this.value = value;
    }*/

/*
        VAT_23 {
        int vatValue() {
            return 23;
        }
    },
    VAT_8 {
        int vatValue() {
            return 8;
        }
    },
    VAT_0 {
        int vatValue() {
            return 0;
        }
    };

    abstract int vatValue();*/


    VAT_23(23),  //calls constructor with value 3
    VAT_8(8),  //calls constructor with value 2
    VAT_0(0)   //calls constructor with value 1
    ; // semicolon needed when fields / methods follow

    private int vatValue;

    private Vat(int vatValue) {
        this.vatValue = vatValue;
    }



}



