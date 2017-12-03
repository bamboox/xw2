package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.Discovery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by bamboo on 17-12-2.
 */
public interface DiscoveryRepository extends ReadOnlyPagingAndSortingRepository<Discovery, String> {

    Page<Discovery> findAllByUserId(String userId, Pageable pageable);

    Page<Discovery> findAll(Pageable pageable);

    Discovery findByUserIdAndId(String userId,String id);
}
