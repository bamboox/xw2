package com.ace.entity.user;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bamboo on 17-11-29.
 */
@Entity
@Data
@EqualsAndHashCode(exclude = "departments")
@Table(name = "organization")
public class Organization extends AbstractTimestampEntity {
    private String name;
    private String address;
    private String type;
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    @JSONField(serialize = false)
    @JsonIgnore
    private Set<Department> departments = new HashSet<>();
}
