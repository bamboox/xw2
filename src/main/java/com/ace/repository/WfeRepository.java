package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.Wfe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by bamboo on 17-12-2.
 */
public interface WfeRepository extends ReadOnlyPagingAndSortingRepository<Wfe, String> {

    Page<Wfe> findAllByTaskSet_ToDepartmentIdAndTaskSet_ToUserIdIsNull(String departmentId, Pageable pageable);

    Page<Wfe> findAllByTaskSet_ToDepartmentIdAndTaskSet_ToUserIdAndTaskSet_NodeType(String departmentId, String userId, String nodeType, Pageable pageable);

    Page<Wfe> findAllByTaskSet_ToDepartmentIdAndTaskSet_ToUserId(String departmentId, String userId, Pageable pageable);
}
