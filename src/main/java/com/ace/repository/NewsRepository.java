package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.News;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by bamboo on 17-12-2.
 */
@CacheConfig(cacheNames = "news")
public interface NewsRepository extends ReadOnlyPagingAndSortingRepository<News, String> {
    @Cacheable
    Page<News> findAllByOrganizationIdAndTitleContainingOrContextContaining(String oId, String title, String Context, Pageable pageable);
    @Cacheable
    Page<News> findAllByOrganizationIdAndToDepartmentJsonContainingAndTitleContainingOrContextContaining(String oId, String departmentId, String title, String Context, Pageable pageable);

    @Override
    @CacheEvict(allEntries=true)
    <S extends News> S save(S paramS);
}
