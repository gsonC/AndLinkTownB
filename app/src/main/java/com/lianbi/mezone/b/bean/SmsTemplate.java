package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * Created by chen.sun on 2016/8/26.
 * 查询短信模板
 */
public class SmsTemplate implements Serializable {

    String templateID;
    String templateMark;
    String projectNo;
    String content;
    boolean  check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public String getTemplateMark() {
        return templateMark;
    }

    public void setTemplateMark(String templateMark) {
        this.templateMark = templateMark;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
