package com.ace.repository;

import com.ace.entity.user.SysUser;

public interface AuthService {
    SysUser register(SysUser userToAdd);

    SysUser login(String username, String password);
    SysUser login(String mobilePhone);

    String refresh(String oldToken);
}
