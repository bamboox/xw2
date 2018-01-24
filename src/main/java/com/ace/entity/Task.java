package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.ace.entity.file.Image;
import com.ace.enums.NodeEnum;
import com.ace.enums.OperateEnum;
import com.ace.enums.TaskEnum;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
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

    @ManyToOne
    @JoinColumn(name = "wfe_id")
    @JSONField(serialize = false)
    private Wfe wfe;

    //节点类型（开始、结束节点或任务节点）Start、End和TaskNode
    private String nodeType;
    @Transient
    private String nodeTypeV;
    //WAIT / COMPLETED
    private String state;
    @Transient
    private String stateV;


    private int orderNo;
    private String nextOperate;
    @Transient
    private String nextOperateV;


    public String getNodeTypeV() {
        if (Strings.isNullOrEmpty(nodeType)) {
            return "";
        } else {
            return NodeEnum.valueOf(nodeType).getValue();
        }
    }

    public String getStateV() {
        if (Strings.isNullOrEmpty(state)) {
            return "";
        } else {
            return TaskEnum.valueOf(state).getValue();
        }
    }

    public String getNextOperateV() {
        if (Strings.isNullOrEmpty(nextOperate)) {
            return "";
        } else {
            String[] split = nextOperate.split(";");
            String[] v = new String[split.length];
            for (int i = 0; i < split.length; i++) {
                v[i] = OperateEnum.valueOf(split[i]).getValue();
            }
            return Joiner.on(";").join(v);
        }
    }

    /*public void setStateV(String stateV) {
        this.stateV = TaskEnum.valueOf(state).getValue();
    }*/

    /*public void setNextOperateV(String nextOperateV) {
        String[] split = nextOperate.split(";");
        String[] v = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            v[i] = OperateEnum.valueOf(split[i]).getValue();
        }
        this.nextOperateV = Joiner.on(";").join(v);
    }*/

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
