package com.ace.entity.user;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bamboo on 17-11-29.
 */
@Entity
@Data
@Table(name = "department")
@EqualsAndHashCode(exclude = "sysUsers")
public class Department extends AbstractTimestampEntity {

    private String name;
    private String address;
    private String linkman;
    private String contactPerson;
    private String contactNumber;
    private String type;
    private String typeCode;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JSONField(serialize = false)
    @JsonIgnore
    private Set<SysUser> sysUsers = new HashSet<>();
}
