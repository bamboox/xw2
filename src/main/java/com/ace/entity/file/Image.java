package com.ace.entity.file;

import com.ace.common.jpa.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bamboo on 17-12-2.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image extends AbstractTimestampEntity {

    String url;
    private String userId;

    /*@JSONField(serialize=false)
    @ManyToOne
    @JoinColumn(name = "discovery_id")
    private Discovery discovery;*/
}
