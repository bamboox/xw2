package com.ace.entity.workflow;

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
 * Created by bamboo on 17-12-5.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude="flowActions")
@Table(name = "flow_info")
public class FlowInfo  extends AbstractTimestampEntity {
    private String flowName;
    @OneToMany(mappedBy = "flowInfo",cascade= CascadeType.ALL)
    private Set<FlowAction> flowActions = new HashSet<>();
}
