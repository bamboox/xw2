package com.ace.entity.wfe;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by bamboo on 17-12-6.
 */
//过程节点@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "process")
@Entity
@Data
@Table(name = "wfe_task")
public class WfeTask extends AbstractTimestampEntity {

    //节点（任务）名称
    private String taskName;
    //节点类型（开始、结束节点或任务节点）Start、End和TaskNode
    private String nodeType;
    //节点的过程逻辑属性（是否分支或合并节点） Line、AndSplit、AndJoin、OrSplit、OrJoin
    private String processLogic;
    //分配的任务角色编号
    //department
    private String assignedRole;
    //是否可以上载或下载附件
    private String aboutAttached;
    //当前任务可读的业务数据表字段名集和
    private String readableFields;
    //当前任务可编辑的业务数据表字段名集和
    private String writableFields;
    //当前任务的承诺完成期限（天）
    private int dueDate;
    //当前任务可打印的表格编号集合
    private String printedTable;

    @ManyToOne
    @JoinColumn(name = "process_id")
    @JSONField(serialize = false)
    private Process process;
}
