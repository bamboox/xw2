package com.ace.service.wfe;

import com.ace.entity.wfe.Process;
import com.ace.entity.wfe.*;
import com.ace.repository.wfe.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by bamboo on 17-12-7.
 */
@Service
public class ProcessInstanceService implements IProcessInstanceService {
    ProcessInstanceRepository processInstanceRepository;
    ProcessRepository processRepository;
    WfeTaskRepository wfeTaskRepository;
    TransferRepository transferRepository;
    TaskInstanceRepository taskInstanceRepository;

    @Autowired
    public ProcessInstanceService(ProcessInstanceRepository processInstanceRepository, ProcessRepository processRepository, WfeTaskRepository wfeTaskRepository, TransferRepository transferRepository, TaskInstanceRepository taskInstanceRepository) {
        this.processInstanceRepository = processInstanceRepository;
        this.processRepository = processRepository;
        this.wfeTaskRepository = wfeTaskRepository;
        this.transferRepository = transferRepository;
        this.taskInstanceRepository = taskInstanceRepository;
    }

    private final static String processId = "1";

    //
    @Override
    public void createProcessInstance(String discoveryId, String userId) {
        //
        Process process = processRepository.findOne(processId);

        WfeTask start = wfeTaskRepository.findByNodeTypeAndProcess_id("START", processId);


        List<Transfer> toTransfers = transferRepository.findByFromTaskIdAndProcess_id(start.getId(), processId);

        String processInstanceId = UUID.randomUUID().toString();
        String startTaskInstanceId = UUID.randomUUID().toString();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setDescription("");
        processInstance.setIdentityFieldValue(discoveryId);
        processInstance.setProcessId(process.getId());
        processInstance.setRelatedTable(process.getRelatedTable());
        processInstance.setIdentifiedField(process.getIdentifiedField());
        processInstance.setId(processInstanceId);
        processInstance.setStartTaskInstanceId(startTaskInstanceId);
        processInstanceRepository.save(processInstance);

        TaskInstance taskInstance = new TaskInstance();
        taskInstance.setTaskState("COMPLETED");
        taskInstance.setTaskId(start.getId());
        taskInstance.setTaskUserID(userId);
        taskInstance.setStartTime(LocalDateTime.now());
        taskInstance.setEndTime(LocalDateTime.now());
        taskInstance.setProcessInstanceId(processInstanceId);

        String nextTaskInstanceSet = "";
        for (Transfer transfer : toTransfers) {
            //
            String nextTaskInstanceId = UUID.randomUUID().toString();
            taskInstance = new TaskInstance();
            taskInstance.setTaskId(transfer.getToTaskId());
            taskInstance.setStartTime(LocalDateTime.now());
            taskInstance.setTaskState("JUST_CREATED");

            taskInstanceRepository.save(taskInstance);

            nextTaskInstanceSet.concat(nextTaskInstanceId).concat(";");
        }
        taskInstance.setNextTaskInstance(nextTaskInstanceSet);
        taskInstanceRepository.save(taskInstance);

    }

    @Override
    public List<TaskInstance> getJustCreatedProcessInstance(String departmentId) {
        WfeTask task = wfeTaskRepository.findByAssignedRoleAndProcess_id(departmentId, processId);
        return taskInstanceRepository.findByTaskIdAndTaskState(task.getId(), "JUST_CREATED");

    }

    @Override
    public List<TaskInstance> getRunningProcessInstance(String departmentId) {
        WfeTask task = wfeTaskRepository.findByAssignedRoleAndProcess_id(departmentId, processId);
        return taskInstanceRepository.findByTaskIdAndTaskState(task.getId(), "RUNNING");
    }

    @Override
    public List<TaskInstance> getCompletedProcessInstance(String departmentId) {
        WfeTask task = wfeTaskRepository.findByAssignedRoleAndProcess_id(departmentId, processId);
        return taskInstanceRepository.findByTaskIdAndTaskState(task.getId(), "COMPLETED");
    }

    @Override
    public void doNextProcessInstance(String taskInstanceId, String userId) {
        TaskInstance taskInstance = taskInstanceRepository.findOne(taskInstanceId);
        WfeTask task = wfeTaskRepository.findOne(taskInstance.getTaskId());

        if ("TASK_NODE".equals(task.getNodeType())) {
            List<Transfer> toTransfers = transferRepository.findByFromTaskIdAndProcess_id(task.getId(), processId);
            String nextTaskInstanceSet = "";
            for (Transfer transfer : toTransfers) {
                //
                String nextTaskInstanceId = UUID.randomUUID().toString();
                taskInstance = new TaskInstance();
                taskInstance.setTaskId(transfer.getToTaskId());
                taskInstance.setStartTime(LocalDateTime.now());
                taskInstance.setTaskState("JUST_CREATED");

                taskInstanceRepository.save(taskInstance);

                nextTaskInstanceSet.concat(nextTaskInstanceId).concat(";");
            }
            taskInstance.setNextTaskInstance(nextTaskInstanceSet);
            taskInstance.setTaskState("COMPLETED");
            taskInstance.setEndTime(LocalDateTime.now());
            taskInstance.setTaskUserID(userId);
            taskInstance.setPreTaskInstance(taskInstance.getProcessInstanceId().concat(";"));
            taskInstanceRepository.save(taskInstance);
        } else if ("END".equals(task.getNodeType())) {

            ProcessInstance processInstance = processInstanceRepository.findOne(taskInstance.getProcessInstanceId());
            processInstance.setEndTime(LocalDateTime.now());
            processInstanceRepository.save(processInstance);
        }
    }
}