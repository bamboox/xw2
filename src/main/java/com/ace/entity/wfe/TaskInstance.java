package com.ace.entity.wfe;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by bamboo on 17-12-7.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"tasks", "transfers"})
@Table(name = "wfe_task_instance")
public class TaskInstance extends AbstractTimestampEntity{
    //任务定义编号
    private String taskId;
    //对应的流程实例编号
    private String processInstanceId;
    //任务开始时间
    LocalDateTime startTime;
    //任务结束时间
    LocalDateTime endTime;
    //拾起任务的用户编号
    private String taskUserID;
    //后续任务实例编号集合（逗号隔开）
    private String nextTaskInstance;
    //前驱任务实例编号集合（逗号隔开）
    private String preTaskInstance;
    //任务执行状态
    private String taskState;
}
