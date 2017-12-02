package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author bamboo
 */
public interface SysUserRepository extends ReadOnlyPagingAndSortingRepository<SysUser, Long> {
    SysUser findByUsername(String username);
}
