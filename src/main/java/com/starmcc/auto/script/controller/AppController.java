package com.starmcc.auto.script.controller;


import com.starmcc.auto.script.entity.ScriptParam;
import com.starmcc.auto.script.service.JobService;
import com.starmcc.qmframework.body.QmBody;
import com.starmcc.qmframework.controller.QmCode;
import com.starmcc.qmframework.controller.QmController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("app")
public class AppController extends QmController {

    private static final Logger LOG = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private JobService jobService;

    @PostMapping("/run")
    public String run(@QmBody List<ScriptParam> scriptParams) {
        if (CollectionUtils.isEmpty(scriptParams)) {
            return super.sendJSON(QmCode._2, "執行失敗", null);
        }
        for (ScriptParam scriptParam : scriptParams) {
            if (Objects.isNull(scriptParam.getSecond()) || scriptParam.getSecond() < 1) {
                return super.sendJSON(QmCode._2, "錯誤的間隔時間", null);
            }
            if (StringUtils.isEmpty(scriptParam.getKeyCode())) {
                return super.sendJSON(QmCode._2, "錯誤的按鍵", null);
            }
            if (Objects.isNull(scriptParam.getErrNum()) || scriptParam.getErrNum() < 0) {
                return super.sendJSON(QmCode._2, "錯誤的容错数", null);
            }
        }
        try {
            jobService.runJob(scriptParams);
        } catch (Exception e) {
            LOG.error("出现错误!", e);
            return super.sendJSON(QmCode._2, "執行失敗", null);
        }
        return super.sendJSON(QmCode._1, "已啓動", null);
    }

    @GetMapping("/stop")
    public String stop() {
        try {
            jobService.stopJob();
        } catch (Exception e) {
            LOG.error("出现错误!", e);
            return super.sendJSON(QmCode._2, "執行失敗", null);
        }
        return super.sendJSON(QmCode._1, "已停止", null);
    }
}