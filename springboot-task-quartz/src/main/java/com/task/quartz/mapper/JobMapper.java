package com.task.quartz.mapper;

import com.task.quartz.entity.domain.JobAndTrigger;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * Job Mapper
 * </p>
 */
@Component
public interface JobMapper {
    /**
     * 查询定时作业和触发器列表
     *
     * @return 定时作业和触发器列表
     */
    List<JobAndTrigger> list();
}
