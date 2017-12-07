package com.ace.repository.wfe;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.wfe.TaskInstance;

import java.util.List;

/**
 * Created by bamboo on 17-12-2.
 */
public interface TaskInstanceRepository extends ReadOnlyPagingAndSortingRepository<TaskInstance, String> {
    List<TaskInstance> findByTaskIdAndTaskState(String taskId, String taskState);
}


