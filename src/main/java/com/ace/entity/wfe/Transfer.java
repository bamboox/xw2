package com.ace.entity.wfe;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by bamboo on 17-12-6.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wfe_transfer")
public class Transfer extends AbstractTimestampEntity {
    //迁移指向的节点名称
    private String toTaskName;
    private String toTaskId;
    //迁移的起始节点名称
    private String fromTaskName;
    private String fromTaskId;

    @ManyToOne
    @JoinColumn(name = "process_id")
    @JSONField(serialize = false)
    private Process process;
}
