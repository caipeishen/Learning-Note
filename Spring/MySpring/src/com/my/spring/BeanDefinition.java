package com.my.spring;

/**
 * @author caipeishen
 * @version 1.0
 * @date 2022/8/11 16:16
 */
public class BeanDefinition {

    private Class type;
    private String scope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
