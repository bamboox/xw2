package com.ace.entity.workflow;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by bamboo on 17-12-5.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flow_action")
public class FlowAction extends AbstractTimestampEntity {


    @ManyToOne
    @JoinColumn(name = "flowInfo_id")
    private FlowInfo flowInfo;

    private String actionName;

    //0为顺序，1为并行
    private int stepType=0;

    private String nextUserId;
    private String nextDepartmentId;

}
