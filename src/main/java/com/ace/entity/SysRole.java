package com.ace.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author bamboo
 */
@Entity
@Data
public class SysRole extends AbstractTimestampEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    private String name;

}
