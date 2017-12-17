package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.Wfe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by bamboo on 17-12-2.
 */
public interface WfeRepository extends ReadOnlyPagingAndSortingRepository<Wfe, String> {

    Page<Wfe> findDistinctByTaskSet_ToDepartmentIdAndTaskSet_ToUserIdIsNull(String departmentId, Pageable pageable);

    Page<Wfe> findDistinctByTaskSet_ToDepartmentIdAndTaskSet_ToUserIdAndTaskSet_NodeType(String departmentId, String userId, String nodeType, Pageable pageable);
//    Page<Wfe> findDistinctById(String departmentId, String fromDepartmentId, Pageable pageable);
    List<Wfe> findDistinctById(String id);
    Page<Wfe>  findDistinctByStateNotAndTaskSet_ToDepartmentIdOrTaskSet_FromDepartmentId(String state,String toDepartmentId, String fromDepartmentId, Pageable pageable);
    Page<Wfe> findDistinctByTaskSet_ToDepartmentIdAndTaskSet_ToUserId(String departmentId, String userId, Pageable pageable);

    Page<Wfe> findDistinctByCreateUserId(String userId, Pageable pageable);
}
