package com.ace.repository.wfe;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.wfe.Task;

/**
 * Created by bamboo on 17-12-2.
 */
public interface TaskRepository extends ReadOnlyPagingAndSortingRepository<Task, String> {

    Task findByNodeTypeAndProcess_id(String nodeType, String processId);

    Task findByAssignedRoleAndProcess_id(String assignedRole, String processId);
}
