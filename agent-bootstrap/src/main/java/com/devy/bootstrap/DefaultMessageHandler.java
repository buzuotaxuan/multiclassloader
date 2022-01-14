package com.devy.bootstrap;

import com.devy.intercepter.Advice;
import com.devy.intercepter.AdviceListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @createTime 2022年01月12日 17:03:00
 */
public  class DefaultMessageHandler implements MessageHandler {

  private final Map<String, List<AdviceListener>> eventListeners = new HashMap<String, List<AdviceListener>>();

  @Override
  public void handleOnBefore(Object[] argumentArray,final String listenerClass) throws Throwable {
    try {
      List<AdviceListener> listener=eventListeners.get(listenerClass);
      for (AdviceListener adviceListener:listener){
        if("before".equals(adviceListener.getIntercepter())){
          Advice advice= new Advice(argumentArray);
          adviceListener.before(advice);
        }

      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void put(String className,List<AdviceListener> eventListener){
    eventListeners.put(className,eventListener);
  }

  public List<AdviceListener> getAdviceListener(String className){
    return eventListeners.get(className);
  }
}
