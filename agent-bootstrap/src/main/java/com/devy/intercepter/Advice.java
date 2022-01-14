package com.devy.intercepter;

import java.lang.ref.WeakReference;

/**
 * @Description TODO
 * @createTime 2022年01月11日 18:14:00
 */
public class Advice {

  public Object[] getParameterArray() {
    return parameterArray;
  }

  public void setParameterArray(Object[] parameterArray) {
    this.parameterArray = parameterArray;
  }

  private Object[] parameterArray;

  public  Advice(
      final Object[] parameterArray
     ) {

    this.parameterArray = parameterArray;

  }

}
