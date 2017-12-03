package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bamboo on 17-11-29.
 */

@Data
@EqualsAndHashCode(exclude="imageSet")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discovery")
public class Discovery extends AbstractTimestampEntity {

    private Long latitude;
    private Long longitude;
    private String location;

    private String description;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Image> imageSet = new HashSet<Image>();

    private String userId;
    private String departmentId;
}
