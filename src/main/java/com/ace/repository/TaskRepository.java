package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.Task;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by bamboo on 17-12-2.
 */
@CacheConfig(cacheNames = "wfe")
public interface TaskRepository extends ReadOnlyPagingAndSortingRepository<Task, String> {
    @Cacheable
    Page<Task> findAllByToDepartmentIdAndToUserIdIsNull(String departmentId, Pageable pageable);

    @Cacheable
    Page<Task> findAllByToDepartmentIdAndToUserId(String departmentId, String userId, Pageable pageable);

    @Cacheable
    Task findByWfe_IdAndOrderNo(String wfeId, int orderNo);

    @Override
    @CacheEvict(allEntries = true)
    <S extends Task> S save(S paramS);
}
