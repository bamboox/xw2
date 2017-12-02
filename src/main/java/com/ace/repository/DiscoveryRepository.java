package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.Discovery;
import com.ace.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by bamboo on 17-12-2.
 */
public interface DiscoveryRepository extends ReadOnlyPagingAndSortingRepository<Discovery, String> {
    Page<Discovery> findAllById(String id, Pageable pageable);
}
