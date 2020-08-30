package com.starmcc.auto.script.entity;

/**
 * 脚本参数
 */
public class ScriptParam {

    private Integer second;
    private Integer keyCode;
    // 容错次数
    private Integer errNum;

    public Integer getErrNum() {
        return errNum;
    }

    public void setErrNum(Integer errNum) {
        this.errNum = errNum;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(Integer keyCode) {
        this.keyCode = keyCode;
    }
}