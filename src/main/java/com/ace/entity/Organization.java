package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.Data;

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
@Table(name = "organization")
public class Organization extends AbstractTimestampEntity {
    private String name;
    @OneToMany(mappedBy = "organization",cascade= CascadeType.ALL)
    private Set<Department> departments = new HashSet<Department>();
}
