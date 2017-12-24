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
 * Created by bamboo on 17-12-8.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"taskSet", "discovery"})
@Entity
@Table(name = "wfe")
public class Wfe extends AbstractTimestampEntity {

    private String createUserId;

    private String createDepartmentId;

    private String toDepartmentId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discovery_Id")
    private Discovery discovery;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "wfe")
    @OrderBy("orderNo ASC")
    //    @JSONField(serialize = false)
    private Set<Task> taskSet = new HashSet<>();
    //JUST_CREATED、RUNNING、COMPLETED
    private String state;

    @Transient
    private Task currentTask;
}
