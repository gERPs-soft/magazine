package com.gerps.magazine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Grzesiek on 2018-11-17
 */

@Entity
@Data
@NoArgsConstructor
public class ProductGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
