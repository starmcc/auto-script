package com.starmcc.auto.script.listener;

import com.starmcc.auto.script.entity.ScriptParam;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class KeyboardEvent extends ApplicationEvent{

    public KeyboardEvent(List<ScriptParam> source) {
        super(source);
    }
}