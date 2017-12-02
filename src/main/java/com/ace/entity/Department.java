package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
/**
 * Created by bamboo on 17-11-29.
 */
@Entity
@Data
@Table(name = "department")
public class Department extends AbstractTimestampEntity {

    private String name;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "department",cascade= CascadeType.ALL)
    private Set<SysUser> sysUsers = new HashSet<SysUser>();
}
