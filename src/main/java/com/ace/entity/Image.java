package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by bamboo on 17-12-2.
 */

@Data
@EqualsAndHashCode(exclude="discovery")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image extends AbstractTimestampEntity {

    String url;
    private String userId;

    @ManyToOne
    @JoinColumn(name = "discovery_id")
    private Discovery discovery;
}
