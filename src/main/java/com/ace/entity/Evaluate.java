package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bamboo on 18-1-8.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evaluate")
public class Evaluate extends AbstractTimestampEntity {

    private String url;
    private String organizationId;

}
