package com.ace.dao;

import com.ace.entity.SysUser;

public interface AuthService {
    SysUser register(SysUser userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}
