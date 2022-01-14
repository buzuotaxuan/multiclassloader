package com.devy.bootstrap;

/**
 * @Description TODO
 * @createTime 2022年01月12日 16:49:00
 */
public interface MessageHandler {

  public void handleOnBefore(final Object[] argumentArray,final String listenerClass) throws Throwable;
}
