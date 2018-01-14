package com.ace.entity.user;

import com.ace.common.jpa.AbstractTimestampEntity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author bamboo
 */
@Entity
@Data
@Table(name = "sys_user")
public class SysUser extends AbstractTimestampEntity implements UserDetails {

    private String username;
    private String name;
    @JSONField(serialize = false)
    private String password;
    @JSONField(serialize = false)
    private Date lastPasswordResetDate;
    @JSONField(serialize = false)
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<SysRole> roles;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "department_id")
    private Department department;

    @Transient
    private String currentToken;

    @JSONField(serialize = false)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
//        List<SysRole> roles = this.getRoles();
//        for (SysRole role : roles) {
//            auths.add(new SimpleGrantedAuthority(role.getName()));
            auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }
        return auths;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return this.password;
    }

    @JSONField(serialize = false)
    @Override
    public String getUsername() {
        return this.username;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
