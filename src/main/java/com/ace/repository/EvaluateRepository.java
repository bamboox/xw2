package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.Evaluate;

/**
 * Created by bamboo on 17-12-2.
 */
public interface EvaluateRepository extends ReadOnlyPagingAndSortingRepository<Evaluate, String> {
    Evaluate findByOrganizationId(String oId);

}
