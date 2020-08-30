package com.starmcc.auto.script.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class KeyboardJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(KeyboardJob.class);


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Map<String, Object> parameter = (Map<String, Object>) jobExecutionContext.getMergedJobDataMap().get("parameter");
        Object val = parameter.get("keyCode");
        Object val2 = parameter.get("errNum");
        if (Objects.isNull(val)) {
            return;
        }
        if (Objects.isNull(val2)) {
            val2 = 1;
        }
        Integer keyCode = (Integer) val;
        Integer errNum = (Integer) val2;
        if (errNum.compareTo(0) == 0) {
            errNum++;
        }
        LOG.info("按鍵: {}, code: {}", KeyEvent.getKeyText(keyCode), keyCode);
        try {
            for (int i = 0; i < errNum; i++) {
                Robot robot = new Robot();//创建自动化工具对象
                if (i > 0) {
                    int random = new Random().nextInt(500);
                    robot.delay(random + 1000);
                }
                robot.keyPress(keyCode);//按下
                robot.delay(new Random().nextInt(500));
                robot.keyRelease(keyCode);//释放
            }
        } catch (Exception e) {
            LOG.error("按鍵異常!", e);
        }
    }
}