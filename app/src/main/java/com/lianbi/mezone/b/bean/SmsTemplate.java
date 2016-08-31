package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * Created by chen.sun on 2016/8/26.
 * 查询短信模板
 */
public class SmsTemplate implements Serializable {

    private static final long serialVersionUID = -7722248124833138228L;
    private String templateID;
    private String templateMark;
    private String projectNo;
    private String content;
    private boolean  check;
    private String templateType;

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public SmsTemplate() {

    }

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
