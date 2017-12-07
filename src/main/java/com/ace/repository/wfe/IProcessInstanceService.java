package com.ace.repository.wfe;

import com.ace.entity.wfe.TaskInstance;

import java.util.List;

/**
 * Created by bamboo on 17-12-2.
 */
public interface IProcessInstanceService {

    void createProcessInstance(String discoveryId, String userId);

    List<TaskInstance> getJustCreatedProcessInstance(String departmentId);

    List<TaskInstance> getRunningProcessInstance(String departmentId);

    List<TaskInstance> getCompletedProcessInstance(String departmentId);

    void doNextProcessInstance(String taskInstanceId, String userId);

}
