package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by bamboo on 17-12-2.
 */
public interface TaskRepository extends ReadOnlyPagingAndSortingRepository<Task, String> {

    Page<Task> findAllByToDepartmentIdAndToUserIdIsNull(String departmentId, Pageable pageable);

    Page<Task> findAllByToDepartmentIdAndToUserId(String departmentId, String userId, Pageable pageable);

    Task findByWfe_IdAndOrderNo(String wfeId, int orderNo);

}
