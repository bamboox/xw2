package com.ace.repository.wfe;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.wfe.Transfer;

import java.util.List;

/**
 * Created by bamboo on 17-12-2.
 */
public interface TransferRepository extends ReadOnlyPagingAndSortingRepository<Transfer, String> {

    List<Transfer> findByFromTaskIdAndProcess_id(String fromTaskId, String processId);
}
