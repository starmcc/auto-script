package com.starmcc.auto.script.listener;

import com.starmcc.auto.script.entity.ScriptParam;
import com.starmcc.auto.script.quartz.KeyboardJob;
import com.starmcc.auto.script.util.QuartzManager;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class KeyListener implements ApplicationListener<KeyboardEvent> {


    @Async
    @Override
    public void onApplicationEvent(KeyboardEvent event) {
        List<ScriptParam> params = (List<ScriptParam>) event.getSource();
        QuartzManager.shutdownJobs();
        QuartzManager.removeJobAll();
        for (ScriptParam scriptParam : params) {
            Map<String, Object> param = new HashMap<>();
            param.put("keyCode", scriptParam.getKeyCode());
            param.put("errNum", scriptParam.getErrNum());
            QuartzManager.addJob(System.currentTimeMillis() + "", KeyboardJob.class, scriptParam.getSecond(), param);
        }
        new Timer().schedule(new TimerTask() {
            public void run() {
                QuartzManager.startJobs();
                this.cancel();
            }
        }, 5000);

    }
}