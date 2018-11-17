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
public class PackageUnit {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

}
