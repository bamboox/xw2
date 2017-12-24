package com.ace.entity.user;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.alibaba.fastjson.annotation.JSONField;
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
    @ManyToOne
    @JoinColumn(name = "organization_id")
    @JSONField(serialize = false)
    private Organization organization;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JSONField(serialize = false)
    private Set<SysUser> sysUsers = new HashSet<>();
}
