package com.ace.entity;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.ace.util.SaltHelper;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.TreeMap;

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
    private long size;
    @Transient
    private String currentVersion;

    public String getCurrentVersion() {
        return version.concat("_").concat(versionFix);
    }
    @Transient
    private String sign;

    public String getSign() {
        TreeMap<String,String> treeMap= Maps.newTreeMap();
        treeMap.put("version",getCurrentVersion());
        treeMap.put("size", String.valueOf(getSize()));
        return SaltHelper.buildSign(treeMap,"ACE");
    }
}
