package com.ace.entity.workflow;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bamboo on 17-12-5.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flow_process")
public class FlowProcess extends AbstractTimestampEntity {
    private String resourceId;
    private String flowId;
    private String stepId;
    //0--不通过，1--通过，2--退回，3--否决，4--撤回
    private String stepAction;
    private String operationUserId;
    private String comment;

}
