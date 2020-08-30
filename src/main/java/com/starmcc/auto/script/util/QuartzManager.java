package com.starmcc.auto.script.util;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.*;

public class QuartzManager {

    private static final SchedulerFactory G_SCHEDULER_FACTORY = new StdSchedulerFactory(); // 创建一个SchedulerFactory工厂实例
    private static final String JOB_GROUP_NAME = "JOBGROUP_NAME"; // 任务组
    private static final String TRIGGER_GROUP_NAME = "TRIGGERGROUP_NAME"; // 触发器组
    private static final Set<String> JOB_NAME_SET = new HashSet<>();

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 （带参数）
     *
     * @param jobName 任务名
     * @param cls     任务
     * @param time    时间设置
     */
    public static void addJob(String jobName, Class<? extends Job> cls, int time, Map<String, Object> parameter) {
        try {
            Scheduler sched = G_SCHEDULER_FACTORY.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).build();
            if (Objects.nonNull(parameter)) {
                // 传参数
                jobDetail.getJobDataMap().put("parameter", parameter);
            }
            // 创建一个新的TriggerBuilder来规范一个触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    // 给触发器起一个名字和组名
                    .withIdentity(jobName, TRIGGER_GROUP_NAME)
                    //立即生效
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            //每隔秒执行一次
                            .withIntervalInSeconds(time)
                            //一直执行
                            .repeatForever())
                    .build();
            sched.scheduleJob(jobDetail, trigger);
            JOB_NAME_SET.add(jobName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName 任务名称
     */
    public static void removeJob(String jobName) {
        try {
            Scheduler sched = G_SCHEDULER_FACTORY.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME); // 通过触发器名和组名获取TriggerKey
            JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME); // 通过任务名和组名获取JobKey
            sched.pauseTrigger(triggerKey); // 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(jobKey); // 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeJobAll() {
        Iterator<String> iterator = JOB_NAME_SET.iterator();
        while (iterator.hasNext()) {
            String jobName = iterator.next();
            QuartzManager.removeJob(jobName);
            iterator.remove();
        }
    }

    /**
     * 启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler sched = G_SCHEDULER_FACTORY.getScheduler();
            if (!sched.isStarted()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = G_SCHEDULER_FACTORY.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}