package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.Wfe;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by bamboo on 17-12-2.
 */
@CacheConfig(cacheNames = "wfe")
public interface WfeRepository extends ReadOnlyPagingAndSortingRepository<Wfe, String> {
    @Cacheable
    Page<Wfe> findDistinctByTaskSet_ToDepartmentIdAndTaskSet_ToUserIdIsNull(String departmentId, Pageable pageable);

    @Cacheable
    Page<Wfe> findDistinctByTaskSet_ToDepartmentIdAndTaskSet_ToUserIdAndTaskSet_NodeType(String departmentId, String userId, String nodeType, Pageable pageable);

    //    Page<Wfe> findDistinctById(String departmentId, String fromDepartmentId, Pageable pageable);
    List<Wfe> findDistinctById(String id);

    @Cacheable
    Page<Wfe> findDistinctByStateNotAndTaskSet_ToDepartmentIdOrTaskSet_FromDepartmentId(String state, String toDepartmentId, String fromDepartmentId, Pageable pageable);

    @Cacheable
    Page<Wfe> findDistinctByTaskSet_ToDepartmentIdAndTaskSet_ToUserId(String departmentId, String userId, Pageable pageable);

    @Cacheable
    Page<Wfe> findDistinctByCreateUserId(String userId, Pageable pageable);

    @CacheEvict(allEntries = true)
    <S extends Wfe> S save(S paramS);

    @Override
    @CacheEvict(allEntries = true)
    void delete(String paramID);
}
