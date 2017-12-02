package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bamboo on 17-11-29.
 */
@Entity
@Data
@Table(name = "discovery")
public class Discovery extends AbstractTimestampEntity {

    private Long latitude;
    private Long longitude;
    private String location;

    private String description;

    @OneToMany(mappedBy = "discovery", cascade = CascadeType.ALL)
    private Set<Image> imageSet = new HashSet<Image>();

    private String userId;
    private String departmentId;
}
