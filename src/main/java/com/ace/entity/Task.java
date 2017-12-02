package com.ace.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by bamboo on 17-11-29.
 */
@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @ManyToOne
    @JoinColumn(name = "discovery_id")
    private Discovery discovery;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private SysUser sysUser;
    // send , yes , no
    private int state;

    private String start_user_id;
    private String start_department_id;

    private String next_user_id;
    private String next_department_id;

}