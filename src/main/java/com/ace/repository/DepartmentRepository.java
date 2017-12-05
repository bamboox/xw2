package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.user.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentRepository extends ReadOnlyPagingAndSortingRepository<Department, String> {
    Page<Department> findAllByOrganization_IdAndNameContaining(String oId,String name, Pageable pageable);
}
