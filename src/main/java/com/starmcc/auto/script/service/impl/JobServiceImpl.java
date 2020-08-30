package com.starmcc.auto.script.service.impl;

import com.starmcc.auto.script.entity.ScriptParam;
import com.starmcc.auto.script.listener.KeyboardEvent;
import com.starmcc.auto.script.service.JobService;
import com.starmcc.auto.script.util.QuartzManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: qm
 * @Date: 2020/8/30 2:00
 */
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private ConfigurableApplicationContext context;

    @Override
    public void runJob(List<ScriptParam> scriptParams) throws Exception {
        context.publishEvent(new KeyboardEvent(scriptParams));
    }

    @Override
    public void stopJob() throws Exception {
        QuartzManager.removeJobAll();
        QuartzManager.startJobs();
    }
}
