package com.ace.repository.wfe;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.wfe.WfeTask;

/**
 * Created by bamboo on 17-12-2.
 */
public interface WfeTaskRepository extends ReadOnlyPagingAndSortingRepository<WfeTask, String> {

    WfeTask findByNodeTypeAndProcess_id(String nodeType, String processId);

    WfeTask findByAssignedRoleAndProcess_id(String assignedRole, String processId);
}
