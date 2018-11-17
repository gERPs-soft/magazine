package com.gerps.magazine.entity;

/**
 * Created by Grzesiek on 2018-11-17
 */

public enum Vat {
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

    abstract int vatValue();
}
