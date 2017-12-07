package com.ace.entity.wfe;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.ace.entity.file.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bamboo on 17-12-7.
 */
//流转详情
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"imageSet"})
@Table(name = "wfe_process_instance_info")
public class ProcessInstanceInfo extends AbstractTimestampEntity {
    private String id;
    private String processInstanceId;
    private String taskInstanceId;

    private String message;
    private String state;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @OrderBy("gmtCreated ASC")
    private Set<Image> imageSet = new HashSet<Image>();
}
