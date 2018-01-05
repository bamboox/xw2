package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.user.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentRepository extends ReadOnlyPagingAndSortingRepository<Department, String> {
    Page<Department> findAllByOrganization_IdAndNameContaining(String oId, String name, Pageable pageable);

    Department findByOrganization_IdAndTypeCode(String oId, String typeCode);

    List<Department> findAllByOrganization_Id(String oId);

    Page<Department> findAllByOrganization_IdAndNameContainingAndIdNot(String oId, String name, String thisId, Pageable pageable);
}
