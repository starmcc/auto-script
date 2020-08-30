package com.starmcc.auto.script.service;

import com.starmcc.auto.script.entity.ScriptParam;

import java.util.List;

/**
 * @Author: qm
 * @Date: 2020/8/30 2:00
 */
public interface JobService {

    void runJob(List<ScriptParam> scriptParams)  throws Exception;

    void stopJob()  throws Exception;
}
