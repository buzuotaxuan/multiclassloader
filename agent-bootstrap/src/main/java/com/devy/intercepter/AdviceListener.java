package com.devy.intercepter;

public interface AdviceListener {

  public String getMethod() ;

  public String getIntercepter();

  public String getClassName();


  public void before(Advice advice);
}
