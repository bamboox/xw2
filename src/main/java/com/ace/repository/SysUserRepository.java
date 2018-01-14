package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.user.SysUser;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author bamboo
 */
//@CacheConfig(cacheNames = "SysUser")
public interface SysUserRepository extends ReadOnlyPagingAndSortingRepository<SysUser, String> {
    @Cacheable(value = "SYS_USER", key = "#p0")
    SysUser findByUsername(String username);

    @Override
    @CachePut(value = "SYS_USER", key = "#p0.username")
    SysUser save(SysUser paramS);
}
