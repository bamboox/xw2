package com.ace.entity.wfe;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Created by bamboo on 17-12-6.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"tasks", "transfers"})
@Table(name = "wfe_process_instance")
public class ProcessInstance extends AbstractTimestampEntity {
    //业务实例的内容描述
    private String description;
    //流程定义编号
    private String processId;
    private LocalDateTime endTime;
    //开始任务实例编号
    private String startTaskInstanceId;
    //过程相关的业务数据表名称
    private String relatedTable;
    private String identifiedField;
    //关联业务表的记录编号值
    private String identityFieldValue;
    //Y(N) 是否冻结当前流程实例
    private boolean isSuspended;
    //冻结时间
    private int suspendedTime;
    //Y(N) 是否取消当前流程
    private boolean isCanceled;
    //取消流程的时间
    private Date canceledTime;
    //取消流程的原因
    private String canceledReason;
    //记录每个环节的任务名称与完成人，以及用户审核意见
    //TODO add table
    private String message;
}
