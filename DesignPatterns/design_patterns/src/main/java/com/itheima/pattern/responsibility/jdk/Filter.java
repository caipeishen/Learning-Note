package com.itheima.pattern.responsibility.jdk;

/**
 * @version v1.0
 * @ClassName: Filter
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 黑马程序员
 */
public interface Filter {
    public void doFilter(Request req,Response res,FilterChain c);
}
