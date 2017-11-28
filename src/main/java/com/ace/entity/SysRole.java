package com.ace.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author bamboo
 */
@Entity
@Data
public class SysRole {
    @Id
    @GeneratedValue
    private Long id;
    private String name;



}
