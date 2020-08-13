package com.demo.shedlock.service;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: QL Zhang
 * @time: 2020/08/11 13:30
 **/

@Service
public class DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

    @Scheduled(cron = "0/5 * * * * ?")
    @SchedulerLock(name = "pushInstConfSchedule", lockAtMostFor = "5s", lockAtLeastFor = "5s")
    public void pushInstConfSchedule(){
        //TODO:实现
        logger.info("执行定时任务。。。");
    }



}
