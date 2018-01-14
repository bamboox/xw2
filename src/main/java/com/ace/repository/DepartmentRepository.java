package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.user.Department;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
@CacheConfig(cacheNames = "DEPARTMENT")
public interface DepartmentRepository extends ReadOnlyPagingAndSortingRepository<Department, String> {
    @Cacheable
    Page<Department> findAllByOrganization_IdAndNameContaining(String oId, String name, Pageable pageable);
    @Cacheable
    Department findByOrganization_IdAndTypeCode(String oId, String typeCode);
    @Cacheable
    List<Department> findAllByOrganization_Id(String oId);
    @Cacheable
    Page<Department> findAllByOrganization_IdAndNameContainingAndIdNot(String oId, String name, String thisId, Pageable pageable);
}
