package com.sldingtest.beans;

import java.io.Serializable;

public class DoneItemBean implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 252245371243062890L;
    private int id;
    private String content;
    private String date;
    private String time;
    
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    
}
