package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bamboo on 18-1-5.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class News extends AbstractTimestampEntity {
    private String context;
    private String toDepartmentJson;
    private String title;
    private String files;
    private String fromUserId;
    private String fromDepartmentId;
    private String organizationId;

}
