package com.gerps.magazine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Grzesiek on 2018-11-21
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "out_operations_warehouse")  //rozchody
public class OutOperationMagazine {
    //received - przyjecie,przychod
    //dispatched - rozchod, wysylka
    @Id
    @GeneratedValue
    private Long id;

    private String documentId;
    private String documentType;
}
