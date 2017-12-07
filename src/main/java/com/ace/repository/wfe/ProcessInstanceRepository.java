package com.ace.repository.wfe;

import com.ace.common.jpa.ReadOnlyPagingAndSortingRepository;
import com.ace.entity.wfe.ProcessInstance;

/**
 * Created by bamboo on 17-12-2.
 */
public interface ProcessInstanceRepository extends ReadOnlyPagingAndSortingRepository<ProcessInstance, String> {


}
