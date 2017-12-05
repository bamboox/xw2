package com.ace.repository;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.file.Image;

/**
 * Created by bamboo on 17-12-2.
 */
public interface ImageRepository extends ReadOnlyPagingAndSortingRepository<Image, String> {
}
