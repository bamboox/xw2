package com.ace.entity.wfe;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bamboo on 17-12-6.
 */
//工作流
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"tasks", "transfers"})
@Table(name = "wfe_process")
public class Process extends AbstractTimestampEntity {
    //过程定义名称
    private String processName;
    //与当前过程定义相关联的业务数据表名称
    private String relatedTable;
    //业务数据表中文名称
    private String alias;
    //业务数据表的标识字段名称
    private String identifiedField;
    //当前过程是否已被完整定义（只有定义完整的过程才可用）
    private boolean isDefineCompleted;
    //当前过程是否被挂起（被挂起的过程不可用
    private boolean isSuspended;
    //业务过程的承诺办理期限（单位为天）
    private int dueDate;

    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
    private Set<Transfer> transfers = new HashSet<>();
}
