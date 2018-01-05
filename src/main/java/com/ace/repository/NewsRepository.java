package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by bamboo on 17-12-2.
 */
public interface NewsRepository extends ReadOnlyPagingAndSortingRepository<News, String> {
    Page<News> findAllByOrganizationIdAndTitleContainingOrContextContaining(String oId, String title, String Context, Pageable pageable);

    Page<News> findAllByOrganizationIdAndToDepartmentJsonContainingAndTitleContainingOrContextContaining(String oId, String departmentId, String title, String Context, Pageable pageable);
}
