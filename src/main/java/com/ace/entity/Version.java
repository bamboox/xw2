package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by bamboo on 17-12-25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "version")
public class Version extends AbstractTimestampEntity {
    @JSONField(serialize = false)
    private String version;
    @JSONField(serialize = false)
    private String versionFix;
    private String url;
    @Transient
    private String currentVersion;

    public String getCurrentVersion() {
        return version.concat("_").concat(versionFix);
    }
}
