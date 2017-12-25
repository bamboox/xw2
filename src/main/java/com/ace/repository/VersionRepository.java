package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.Version;

/**
 * Created by bamboo on 17-12-2.
 */
public interface VersionRepository extends ReadOnlyPagingAndSortingRepository<Version, String> {
    Version findFirstByVersionOrderByGmtCreatedDesc(String version);

    Version findFirstByOrderByGmtCreatedDesc();
}
