package com.gerps.magazine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

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
@Table(name = "in_operations_warehouse")  ////przychody
public class InOperationMagazine {
    //received - przyjecie,przychod
    //dispatched - rozchod, wysylka
    @Id
    @GeneratedValue
    private Long id;

    private String documentId;
    private String documentType;
}
