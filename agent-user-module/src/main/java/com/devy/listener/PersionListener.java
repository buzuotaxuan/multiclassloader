package com.devy.listener;

import com.devy.intercepter.Advice;
import com.devy.intercepter.AdviceListener;
import com.devy.test.Persion;

/**
 * @Description TODO
 * @createTime 2022年01月11日 18:20:00
 */
public class PersionListener implements AdviceListener {
  private String method;
  private String intercepter;
  private String className;

  @Override
  public String getMethod() {
    return method;
  }

  @Override
  public String getIntercepter() {
    return intercepter;
  }


  @Override
  public String getClassName() {
    return className;
  }


  public PersionListener(String method,String intercepter,String className){
    this.method=method;
    this.intercepter=intercepter;
    this.className=className;
  }

  @Override
  public void before(Advice advice) {
    System.out.println(advice.getParameterArray());
    Persion persion=new Persion();
    persion.setAddress("aaa");
    System.out.println(persion.getAddress());
  }
}
