package com.sorm.bean;

public class JavaFieldGetSet {
    private String fieldInfo; //属性源码信息
    private String getInfo;   //get方法源码信息
    private String setInfo;	  // set方法源码信息
    public JavaFieldGetSet(String fieldInfo, String getInfo, String setInfo) {
        super();
        this.fieldInfo = fieldInfo;
        this.getInfo = getInfo;
        this.setInfo = setInfo;
    }
    public JavaFieldGetSet() {
        super();
    }
    public String getFieldInfo() {
        return fieldInfo;
    }
    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }
    public String getGetInfo() {
        return getInfo;
    }
    public void setGetInfo(String getInfo) {
        this.getInfo = getInfo;
    }
    public String getSetInfo() {
        return setInfo;
    }
    public void setSetInfo(String setInfo) {
        this.setInfo = setInfo;
    }
    public String toString()
    {
        return this.getFieldInfo()+"\n"+this.getGetInfo()+"\n"+this.getSetInfo();
    }
}
