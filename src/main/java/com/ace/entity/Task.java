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

@Data
@EqualsAndHashCode(exclude = {"imageSet", "wfe"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task extends AbstractTimestampEntity {


    private String fromUserId;
    private String fromUserName;

    private String fromDepartmentId;
    private String fromDepartmentName;

    private String toDepartmentId;
    private String toDepartmentName;

    private String toUserId;
    private String toUserName;

    private String message;
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @OrderBy("gmtCreated ASC")
    private Set<Image> imageSet = new HashSet<Image>();

    @ManyToOne(fetch=FetchType.LAZY,optional=true)
    @JoinColumn(name = "wfe_id")
    @JSONField(serialize = false)
    private Wfe wfe;

    //节点类型（开始、结束节点或任务节点）Start、End和TaskNode
    private String nodeType;
    //WAIT / COMPLETED
    private String state;
    private String nextOperate;

    public Task(String fromUserId, String fromUserName, String fromDepartmentId, String fromDepartmentName) {
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
        this.fromDepartmentId = fromDepartmentId;
        this.fromDepartmentName = fromDepartmentName;
    }

    public Task(String fromUserId, String fromUserName, String fromDepartmentId, String fromDepartmentName, String toDepartmentId, String toDepartmentName, String toUserId, String toUserName) {
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
        this.fromDepartmentId = fromDepartmentId;
        this.fromDepartmentName = fromDepartmentName;
        this.toDepartmentId = toDepartmentId;
        this.toDepartmentName = toDepartmentName;
        this.toUserId = toUserId;
        this.toUserName = toUserName;
    }
}
