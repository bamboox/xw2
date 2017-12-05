package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.user.SysUser;

/**
 * @author bamboo
 */
public interface SysUserRepository extends ReadOnlyPagingAndSortingRepository<SysUser, Long> {
    SysUser findByUsername(String username);
}
