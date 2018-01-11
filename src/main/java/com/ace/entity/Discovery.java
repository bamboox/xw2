package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.ace.entity.file.Image;
import com.alibaba.fastjson.annotation.JSONField;
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
@EqualsAndHashCode(exclude = "imageSet")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discovery")
public class Discovery extends AbstractTimestampEntity {

    private Double latitude;
    private Double longitude;
    private String location;
    private String organizationId;
    private String description;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @OrderBy("gmtCreated ASC")
    private Set<Image> imageSet = new HashSet<Image>();
    private String userId;
    private String departmentId;

    @OneToOne(mappedBy = "discovery")
    @JSONField(serialize = false)
    private Wfe wfe;
}
