package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by bamboo on 17-12-2.
 */
@Entity
@Data
@Table(name = "image")
public class Image extends AbstractTimestampEntity {

    String url;
    private String userId;

    @ManyToOne
    @JoinColumn(name = "discovery_id")
    private Discovery discovery;
}
