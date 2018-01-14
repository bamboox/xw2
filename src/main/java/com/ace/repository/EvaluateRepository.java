package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.Evaluate;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

/**
 * Created by bamboo on 17-12-2.
 */

@CacheConfig(cacheNames = "EVALUATE")
public interface EvaluateRepository extends ReadOnlyPagingAndSortingRepository<Evaluate, String> {
    @Cacheable(key="#p0")
    Evaluate findByOrganizationId(String oId);
}
