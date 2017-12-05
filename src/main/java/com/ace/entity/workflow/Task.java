package com.ace.entity.workflow;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.ace.entity.user.SysUser;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by bamboo on 17-11-29.
 */
@Entity
@Data
public class Task extends AbstractTimestampEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SysUser sysUser;
    // 再办 待办 已办
    private int state;

    private String discoveryId;

    private String start_user_id;
    private String start_department_id;


}
