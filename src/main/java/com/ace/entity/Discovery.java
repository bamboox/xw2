package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by bamboo on 17-11-29.
 */
@Entity
@Data
@Table(name = "discovery")
public class Discovery extends AbstractTimestampEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String location;
    private Long latitude;
    private Long longitude;
    private String images;

    /**
     * ManyToOne user
     */
}
